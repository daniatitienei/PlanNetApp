package com.ad_coding.plannettestapp.ui.screen.product_list_two

import com.ad_coding.plannettestapp.domain.model.ProductModel

data class ProductListTwoState(
    val productList: List<ProductModel> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val isErrorDialogVisible: Boolean = false
)
