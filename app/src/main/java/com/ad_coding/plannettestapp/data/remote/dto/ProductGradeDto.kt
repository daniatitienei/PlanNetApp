package com.ad_coding.plannettestapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductGradeDto(
    val productId: Int,
    val name: String,
    val grade: String
)
