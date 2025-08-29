package com.ad_coding.plannettestapp.data.mapper

import com.ad_coding.plannettestapp.data.local.entity.ProductLevelOneEntity
import com.ad_coding.plannettestapp.data.local.entity.ProductLevelTwoEntity
import com.ad_coding.plannettestapp.data.remote.dto.ProductDto
import com.ad_coding.plannettestapp.domain.model.ProductModel

fun ProductDto.toModel() =
    ProductModel(
        id = id,
        name = name,
        description = description,
        clientCount = clientCount
    )

fun ProductModel.toProductLevelOneEntity(timestamp: Long) = ProductLevelOneEntity(
    id = id,
    name = name,
    description = description,
    clientCount = clientCount,
    timestamp = timestamp
)

fun ProductModel.toProductLevelTwoEntity(timestamp: Long) = ProductLevelTwoEntity(
    id = id,
    name = name,
    description = description,
    clientCount = clientCount,
    timestamp = timestamp
)

fun ProductLevelTwoEntity.toModel() = ProductModel(
    id = id,
    name = name,
    description = description,
    clientCount = clientCount
)


fun ProductLevelOneEntity.toModel() = ProductModel(
    id = id,
    name = name,
    description = description,
    clientCount = clientCount
)

