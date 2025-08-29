package com.ad_coding.plannettestapp.ui.screen.grade_list

sealed interface GradeListEvent {
    data object RefreshList : GradeListEvent
    data object DismissError: GradeListEvent
}