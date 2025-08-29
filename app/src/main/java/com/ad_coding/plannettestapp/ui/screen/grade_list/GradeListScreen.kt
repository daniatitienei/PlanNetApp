@file:OptIn(ExperimentalMaterial3Api::class)

package com.ad_coding.plannettestapp.ui.screen.grade_list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.ad_coding.plannettestapp.ui.util.composable.ErrorDialog
import com.ad_coding.plannettestapp.ui.util.composable.PullToRefresh

@Composable
fun GradeListScreen(
    state: GradeListState,
    onEvent: (GradeListEvent) -> Unit
) {
    Scaffold { innerPadding ->
        ErrorDialog(
            isVisible = state.isErrorDialogVisible,
            onDismissRequest = {
                onEvent(GradeListEvent.DismissError)
            },
            text = state.errorMessage ?: "",
            confirmButtonOnClick = {
                onEvent(GradeListEvent.RefreshList)
            },
            dismissButtonOnClick = {
                onEvent(GradeListEvent.DismissError)
            }
        )

        PullToRefresh(
            isRefreshing = state.isRefreshing,
            isLoading = state.isLoading,
            onRefresh = {
                onEvent(GradeListEvent.RefreshList)
            }
        ) {
            LazyColumn(
                contentPadding = PaddingValues(
                    top = innerPadding.calculateTopPadding() + 55.dp,
                    bottom = innerPadding.calculateBottomPadding()
                )
            ) {
                items(items = state.gradeList, key = { it.productId }) { item ->
                    ListItem(
                        headlineContent = {
                            Text(text = "${item.name} - ${item.grade}")
                        }
                    )
                }
            }
        }
    }
}