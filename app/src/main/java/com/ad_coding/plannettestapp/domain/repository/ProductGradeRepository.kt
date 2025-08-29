package com.ad_coding.plannettestapp.domain.repository

import com.ad_coding.plannettestapp.domain.model.ProductGradeModel
import com.ad_coding.plannettestapp.domain.util.DataResult
import kotlinx.coroutines.flow.Flow

interface ProductGradeRepository {

    fun getGradeList(forceRefresh: Boolean): Flow<DataResult<List<ProductGradeModel>>>
}