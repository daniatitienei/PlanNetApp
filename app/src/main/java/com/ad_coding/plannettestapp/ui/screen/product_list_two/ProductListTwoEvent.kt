package com.ad_coding.plannettestapp.ui.screen.product_list_two

import com.ad_coding.plannettestapp.domain.model.ProductModel

sealed interface ProductListTwoEvent {
    data class ProductClick(val product: ProductModel): ProductListTwoEvent
    data object RefreshList: ProductListTwoEvent
    data object DismissError: ProductListTwoEvent
}