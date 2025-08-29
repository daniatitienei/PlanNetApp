package com.ad_coding.plannettestapp.data.repository

import android.util.Log
import com.ad_coding.plannettestapp.data.local.dao.ProductGradeDao
import com.ad_coding.plannettestapp.data.local.entity.ProductGradeEntity
import com.ad_coding.plannettestapp.data.mapper.toEntity
import com.ad_coding.plannettestapp.data.mapper.toModel
import com.ad_coding.plannettestapp.data.remote.ProductsApi
import com.ad_coding.plannettestapp.domain.model.ProductGradeModel
import com.ad_coding.plannettestapp.domain.model.ProductModel
import com.ad_coding.plannettestapp.domain.repository.ProductGradeRepository
import com.ad_coding.plannettestapp.domain.repository.ProductRepository
import com.ad_coding.plannettestapp.domain.util.Clock
import com.ad_coding.plannettestapp.domain.util.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductGradeRepositoryImpl @Inject constructor(
    private val api: ProductsApi,
    private val productRepository: ProductRepository,
    private val dao: ProductGradeDao,
    private val clock: Clock
) : ProductGradeRepository {

    private val cacheExpiryMs = 5 * 60 * 1000

    override fun getGradeList(forceRefresh: Boolean): Flow<DataResult<List<ProductGradeModel>>> =
        flow {
            val cached = dao.getProductGrades()
            val isCacheValid = cached.isNotEmpty() && !isExpired(cached.first().timestamp)

            if (!forceRefresh && isCacheValid) {
                Log.d("getProductsLevelTwo", "Fetched from cache")
                emit(DataResult.Success(cached.map { it.toModel() }))
                return@flow
            }

            val levelOneFlow = productRepository.getProductsLevelOne(forceRefresh)
            val levelTwoFlow = productRepository.getProductsLevelTwo(forceRefresh)

            Log.d("getProductsLevelTwo", "Fetched from network")

            levelOneFlow.combine(levelTwoFlow) { l1, l2 -> l1 to l2 }
                .collect { (level1Result, level2Result) ->
                    val level1 = (level1Result as? DataResult.Success)?.data
                    val level2 = (level2Result as? DataResult.Success)?.data

                    if (level1 == null || level2 == null) {
                        emit(DataResult.Error("Failed to load product data"))
                        return@collect
                    }

                    val grades = fetchGrades(level1, level2)

                    if (grades.isEmpty()) {
                        emit(DataResult.Error("Failed to fetch grades"))
                    } else {
                        dao.replaceProductGradeList(grades)
                        emit(DataResult.Success(grades.map { it.toModel() }))
                    }
                }
        }.catch { e ->
            val cached = dao.getProductGrades()
            if (cached.isNotEmpty()) {
                emit(DataResult.Success(cached.map { it.toModel() }))
            } else {
                emit(DataResult.Error("Network error: ${e.localizedMessage}"))
            }
        }

    private fun isExpired(timestamp: Long): Boolean =
        (clock.now() - timestamp) > cacheExpiryMs

    private suspend fun fetchGrades(
        level1: List<ProductModel>,
        level2: List<ProductModel>
    ): List<ProductGradeEntity> {
        val level1Map = level1.associate { it.id to it.clientCount }
        val level2Map = level2.associate { it.id to it.clientCount }
        val allIds = level1Map.keys + level2Map.keys

        return allIds.mapNotNull { productId ->
            val l1 = level1Map[productId] ?: 0
            val l2 = level2Map[productId] ?: 0
            val response = api.getGrade(productId, l1, l2)
            if (response.isSuccessful) response.body()?.toEntity(clock.now()) else null
        }
    }
}

