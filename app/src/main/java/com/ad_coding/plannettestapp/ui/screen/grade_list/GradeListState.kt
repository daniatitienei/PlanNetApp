package com.ad_coding.plannettestapp.ui.screen.grade_list

import com.ad_coding.plannettestapp.domain.model.ProductGradeModel

data class GradeListState(
    val gradeList: List<ProductGradeModel> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val isErrorDialogVisible: Boolean = false
)
