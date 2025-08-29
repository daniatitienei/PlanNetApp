package com.ad_coding.plannettestapp.ui.screen.product_list_one

import com.ad_coding.plannettestapp.domain.model.ProductModel

sealed interface ProductListOneEvent {
    data class ProductClick(val product: ProductModel): ProductListOneEvent
    data object RefreshList: ProductListOneEvent
    data object DismissError: ProductListOneEvent
}