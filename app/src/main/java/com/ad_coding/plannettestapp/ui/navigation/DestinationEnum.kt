package com.ad_coding.plannettestapp.ui.navigation

enum class DestinationEnum {
    PRODUCT_LIST_ONE,
    PRODUCT_LIST_TWO,
    GRADE_LIST
}

fun DestinationEnum.getRoute() =
    when (this) {
        DestinationEnum.PRODUCT_LIST_ONE -> ProductListOne
        DestinationEnum.PRODUCT_LIST_TWO -> ProductListTwo
        DestinationEnum.GRADE_LIST -> GradeList
    }

fun DestinationEnum.getLabel() =
    when (this) {
        DestinationEnum.PRODUCT_LIST_ONE -> "List One"
        DestinationEnum.PRODUCT_LIST_TWO -> "List Two"
        DestinationEnum.GRADE_LIST -> "Grade List"
    }