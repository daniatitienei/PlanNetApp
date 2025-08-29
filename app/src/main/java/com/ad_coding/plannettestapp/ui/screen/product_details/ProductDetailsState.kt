package com.ad_coding.plannettestapp.ui.screen.product_details

import com.ad_coding.plannettestapp.domain.model.ProductModel

data class ProductDetailsState(
    val product: ProductModel? = null,
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val isErrorDialogVisible: Boolean = false
)
