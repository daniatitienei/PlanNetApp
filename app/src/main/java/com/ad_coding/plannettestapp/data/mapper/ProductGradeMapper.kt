package com.ad_coding.plannettestapp.data.mapper

import com.ad_coding.plannettestapp.data.local.entity.ProductGradeEntity
import com.ad_coding.plannettestapp.data.remote.dto.ProductGradeDto
import com.ad_coding.plannettestapp.domain.model.ProductGradeModel

fun ProductGradeDto.toModel() =
    ProductGradeModel(
        name = name,
        grade = grade,
        productId = productId
    )

fun ProductGradeDto.toEntity(timestamp: Long) = ProductGradeEntity(
    productId = productId,
    name = name,
    grade = grade,
    timestamp = timestamp
)

fun ProductGradeEntity.toModel() = ProductGradeModel(
    productId = productId,
    name = name,
    grade = grade
)

fun ProductGradeModel.toEntity(timestamp: Long) = ProductGradeEntity(
    productId = productId,
    name = name,
    grade = grade,
    timestamp = timestamp
)
