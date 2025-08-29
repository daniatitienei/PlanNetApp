package com.ad_coding.plannettestapp.ui.screen.product_list_one

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ad_coding.plannettestapp.R
import com.ad_coding.plannettestapp.ui.util.composable.ErrorDialog
import com.ad_coding.plannettestapp.ui.util.composable.PullToRefresh

@Composable
fun ProductListOneScreen(
    state: ProductListOneState,
    onEvent: (ProductListOneEvent) -> Unit
) {
    Scaffold { innerPadding ->
        PullToRefresh(
            isRefreshing = state.isLoading,
            onRefresh = {
                onEvent(ProductListOneEvent.RefreshList)
            }
        ) {
            ErrorDialog(
                isVisible = state.isErrorDialogVisible,
                onDismissRequest = {
                    onEvent(ProductListOneEvent.DismissError)
                },
                text = state.errorMessage ?: "",
                confirmButtonOnClick = {
                    onEvent(ProductListOneEvent.RefreshList)
                },
                dismissButtonOnClick = {
                    onEvent(ProductListOneEvent.DismissError)
                }
            )

            LazyColumn(
                contentPadding = PaddingValues(
                    top = innerPadding.calculateTopPadding() + 55.dp,
                    bottom = innerPadding.calculateBottomPadding()
                )
            ) {
                items(items = state.productList, key = { it.id }) { product ->
                    ListItem(
                        headlineContent = {
                            Text(text = product.name)
                        },
                        trailingContent = {
                            Icon(
                                imageVector = Icons.Rounded.ChevronRight,
                                contentDescription = stringResource(
                                    R.string.see_product_details_for,
                                    product.name
                                )
                            )
                        },
                        modifier = Modifier.clickable {
                            onEvent(ProductListOneEvent.ProductClick(product = product))
                        }
                    )
                }
            }
        }
    }
}