package com.ad_coding.plannettestapp.data.repository

import com.ad_coding.plannettestapp.data.local.dao.ProductLevelOneDao
import com.ad_coding.plannettestapp.data.local.dao.ProductLevelTwoDao
import com.ad_coding.plannettestapp.data.local.entity.ProductLevelTwoEntity
import com.ad_coding.plannettestapp.data.mapper.toModel
import com.ad_coding.plannettestapp.data.remote.ProductsApi
import com.ad_coding.plannettestapp.data.remote.dto.ProductDto
import com.ad_coding.plannettestapp.domain.util.Clock
import com.ad_coding.plannettestapp.domain.util.DataResult
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.just
import io.mockk.mockkStatic
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class ProductRepositoryImplTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    private lateinit var api: ProductsApi

    @MockK
    private lateinit var productLevelOneDao: ProductLevelOneDao

    @MockK
    private lateinit var productLevelTwoDao: ProductLevelTwoDao

    @MockK
    private lateinit var clock: Clock

    private lateinit var productRepository: ProductRepositoryImpl

    private val testTimestamp = System.currentTimeMillis()

    @Before
    fun setUp() {
        mockkStatic(android.util.Log::class)
        every { android.util.Log.d(any(), any()) } returns 0
        productRepository = ProductRepositoryImpl(
            api = api,
            productLevelOneDao = productLevelOneDao,
            productLevelTwoDao = productLevelTwoDao,
            clock = clock
        )

        every { clock.now() } returns testTimestamp
    }

    @Test
    fun `getProductsLevelTwo, fetches products from network if cache is empty and forceRefresh is true`() =
        runTest {
            val dtoList = listOf(
                ProductDto(
                    id = 201,
                    name = "Pizza",
                    description = "Cheesy pizza",
                    clientCount = 5
                )
            )
            val productList = dtoList.map { it.toModel() }

            coEvery { productLevelTwoDao.getProducts() } returns emptyList()
            coEvery { api.getProductsLevelTwo() } returns Response.success(dtoList)
            coEvery { productLevelTwoDao.replaceProducts(any()) } just Runs

            val result = productRepository.getProductsLevelTwo(forceRefresh = true).first()

            assertTrue(result is DataResult.Success)
            assertEquals(result.data, productList)
            assertEquals(productList, (result as DataResult.Success).data)

            coVerify(exactly = 1) { api.getProductsLevelTwo() }
            verify(exactly = 1) { clock.now() }
        }

    @Test
    fun `getProductsLevelTwo, fetches products from cache and forceRefresh is false`() =
        runTest {
            val entityList = listOf(
                ProductLevelTwoEntity(
                    id = 201,
                    name = "Pizza",
                    description = "Cheesy pizza",
                    clientCount = 5,
                    timestamp = 1000L
                )
            )
            val productList = entityList.map { it.toModel() }

            coEvery { productLevelTwoDao.getProducts() } returns entityList

            val result = productRepository.getProductsLevelTwo(forceRefresh = false).first()

            assertTrue(result is DataResult.Success)
            assertEquals(result.data, productList)
            assertEquals(productList, (result as DataResult.Success).data)

            verify(exactly = 1) { clock.now() }
        }
}
