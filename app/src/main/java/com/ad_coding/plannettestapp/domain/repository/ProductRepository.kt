package com.ad_coding.plannettestapp.domain.repository

import com.ad_coding.plannettestapp.domain.model.ProductModel
import com.ad_coding.plannettestapp.domain.util.DataResult
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProductsLevelOne(forceRefresh: Boolean): Flow<DataResult<List<ProductModel>>>
    fun getProductsLevelTwo(forceRefresh: Boolean): Flow<DataResult<List<ProductModel>>>

    fun getProductLevelOneById(id: Int): Flow<DataResult<ProductModel>>
    fun getProductLevelTwoById(id: Int): Flow<DataResult<ProductModel>>
}