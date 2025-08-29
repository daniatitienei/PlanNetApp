package com.ad_coding.plannettestapp.domain.use_case

import com.ad_coding.plannettestapp.domain.model.ProductModel
import com.ad_coding.plannettestapp.domain.repository.ProductRepository
import com.ad_coding.plannettestapp.domain.util.DataResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductLevelTwoByIdUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(id: Int): Flow<DataResult<ProductModel>> {
        return repository.getProductLevelTwoById(id)
    }
}