package com.ad_coding.plannettestapp.ui.screen.product_list_one

import com.ad_coding.plannettestapp.domain.model.ProductModel

data class ProductListOneState(
    val productList: List<ProductModel> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val isErrorDialogVisible: Boolean = false
)
