package com.ad_coding.plannettestapp.data.mapper

import com.ad_coding.plannettestapp.data.remote.dto.GradeDto
import com.ad_coding.plannettestapp.domain.model.GradeModel

fun GradeDto.toModel() =
    GradeModel(
        name = name,
        grade = grade,
        productId = productId
    )