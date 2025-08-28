package com.ad_coding.plannettestapp.data.mapper

import com.ad_coding.plannettestapp.data.remote.dto.ProductDto
import com.ad_coding.plannettestapp.domain.model.ProductModel

fun ProductDto.toModel() =
    ProductModel(
        id = id,
        name = name,
        description = description,
        clientCount = clientCount
    )