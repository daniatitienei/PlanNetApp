package com.ad_coding.plannettestapp.domain.use_case

import com.ad_coding.plannettestapp.domain.model.ProductGradeModel
import com.ad_coding.plannettestapp.domain.repository.ProductGradeRepository
import com.ad_coding.plannettestapp.domain.util.DataResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGradeListUseCase @Inject constructor(
    private val repository: ProductGradeRepository
) {
    operator fun invoke(forceRefresh: Boolean = false): Flow<DataResult<List<ProductGradeModel>>> {
        return repository.getGradeList(forceRefresh)
    }
}