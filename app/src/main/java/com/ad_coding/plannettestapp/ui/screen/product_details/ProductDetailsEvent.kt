package com.ad_coding.plannettestapp.ui.screen.product_details

sealed interface ProductDetailsEvent {
    data object NavigateBack: ProductDetailsEvent
    data object DismissError: ProductDetailsEvent
    data object FetchDetails: ProductDetailsEvent
}