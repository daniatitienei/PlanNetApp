package com.ad_coding.plannettestapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val id: Int,
    val name: String,
    val description: String,
    val clientCount: Int
)
