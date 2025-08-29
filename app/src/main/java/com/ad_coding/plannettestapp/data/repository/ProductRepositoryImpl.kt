package com.ad_coding.plannettestapp.data.repository

import android.util.Log
import com.ad_coding.plannettestapp.data.local.dao.ProductLevelOneDao
import com.ad_coding.plannettestapp.data.local.dao.ProductLevelTwoDao
import com.ad_coding.plannettestapp.data.mapper.toEntity
import com.ad_coding.plannettestapp.data.mapper.toModel
import com.ad_coding.plannettestapp.data.mapper.toProductLevelOneEntity
import com.ad_coding.plannettestapp.data.mapper.toProductLevelTwoEntity
import com.ad_coding.plannettestapp.data.remote.ProductsApi
import com.ad_coding.plannettestapp.domain.model.ProductModel
import com.ad_coding.plannettestapp.domain.repository.ProductRepository
import com.ad_coding.plannettestapp.domain.util.Clock
import com.ad_coding.plannettestapp.domain.util.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductsApi,
    private val productLevelOneDao: ProductLevelOneDao,
    private val productLevelTwoDao: ProductLevelTwoDao,
    private val clock: Clock
) : ProductRepository {

    private val cacheExpiryMs = 5 * 60 * 1000

    override fun getProductsLevelOne(forceRefresh: Boolean): Flow<DataResult<List<ProductModel>>> =
        flow {
            val cached = productLevelOneDao.getProducts()
            if (!forceRefresh && cached.isNotEmpty() && !isExpired(cached.first().timestamp)) {
                Log.d("getProductsLevelOne", "Fetched from cache")
                emit(DataResult.Success(cached.map { it.toModel() }))
            } else {
                Log.d("getProductsLevelOne", "Fetched from network")
                val response = api.getProductsLevelOne()
                if (response.isSuccessful) {
                    val products = response.body().orEmpty().map { it.toModel() }
                    productLevelOneDao.replaceProducts(products.map {
                        it.toProductLevelOneEntity(
                            clock.now()
                        )
                    })
                    emit(DataResult.Success(products))
                } else {
                    emit(DataResult.Error("API error: ${response.code()} ${response.message()}"))
                }
            }
        }.catch { e ->
            val cached = productLevelOneDao.getProducts()
            if (cached.isNotEmpty()) {
                emit(DataResult.Success(cached.map { it.toModel() }))
            } else {
                emit(DataResult.Error("Network error: ${e.localizedMessage}"))
            }
        }

    override fun getProductsLevelTwo(forceRefresh: Boolean): Flow<DataResult<List<ProductModel>>> =
        flow {
            val cached = productLevelTwoDao.getProducts()
            if (!forceRefresh && cached.isNotEmpty() && !isExpired(cached.first().timestamp)) {
                Log.d("getProductsLevelTwo", "Fetched from cache")
                emit(DataResult.Success(cached.map { it.toModel() }))
            } else {
                Log.d("getProductsLevelTwo", "Fetched from network")
                val response = api.getProductsLevelTwo()
                if (response.isSuccessful) {
                    val products = response.body().orEmpty().map { it.toModel() }
                    productLevelTwoDao.replaceProducts(products.map {
                        it.toProductLevelTwoEntity(
                            clock.now()
                        )
                    })
                    emit(DataResult.Success(products))
                } else {
                    emit(DataResult.Error("API error: ${response.code()} ${response.message()}"))
                }
            }
        }.catch { e ->
            val cached = productLevelTwoDao.getProducts()
            if (cached.isNotEmpty()) {
                emit(DataResult.Success(cached.map { it.toModel() }))
            } else {
                emit(DataResult.Error("Network error: ${e.localizedMessage}"))
            }
        }

    override fun getProductLevelOneById(id: Int): Flow<DataResult<ProductModel>> =
        flow {
            val cached = productLevelOneDao.getProductByIdAndLevel(id)
            if (cached != null) {
                emit(DataResult.Success(cached.toModel()))
            } else {
                emit(DataResult.Error("Product $id not found in level1 cache"))
            }
        }.catch { e ->
            emit(DataResult.Error("Database error: ${e.localizedMessage}"))
        }

    override fun getProductLevelTwoById(id: Int): Flow<DataResult<ProductModel>> =
        flow {
            val cached = productLevelTwoDao.getProductByIdAndLevel(id)
            if (cached != null) {
                emit(DataResult.Success(cached.toModel()))
            } else {
                emit(DataResult.Error("Product id=$id not found in level2 cache"))
            }
        }.catch { e ->
            emit(DataResult.Error("Database error: ${e.localizedMessage}"))
        }


    private fun isExpired(timestamp: Long): Boolean {
        return (clock.now() - timestamp) > cacheExpiryMs
    }
}

