package com.ad_coding.plannettestapp.data.remote

import com.ad_coding.plannettestapp.data.remote.dto.ProductGradeDto
import com.ad_coding.plannettestapp.data.remote.dto.ProductDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsApi {

    @GET("mobile_test/getProductsLevel1.php")
    suspend fun getProductsLevelOne(): Response<List<ProductDto>>

    @GET("mobile_test/getProductsLevel2.php")
    suspend fun getProductsLevelTwo(): Response<List<ProductDto>>

    @GET("mobile_test/getGrade.php")
    suspend fun getGrade(
        @Query("productId") productId: Int,
        @Query("clientCountLevel1") clientCountLevelOne: Int,
        @Query("clientCountLevel2") clientCountLevelTwo: Int
    ): Response<ProductGradeDto>

    companion object {
        const val BASE_URL = "https://restmock.plan-net.technology/"
    }
}