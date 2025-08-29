package com.ad_coding.plannettestapp.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
data object ProductListOne

@Serializable
data object ProductListTwo

@Serializable
data object GradeList

@Serializable
data class ProductDetails(val productId: Int, val level: String)