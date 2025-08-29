@file:OptIn(ExperimentalMaterial3Api::class)

package com.ad_coding.plannettestapp.ui.screen.product_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ad_coding.plannettestapp.R
import com.ad_coding.plannettestapp.ui.util.composable.ErrorDialog

@Composable
fun ProductDetailsScreen(
    state: ProductDetailsState,
    onEvent: (ProductDetailsEvent) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Product details")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onEvent(ProductDetailsEvent.NavigateBack)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ChevronLeft,
                            contentDescription = stringResource(R.string.navigate_back),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        ErrorDialog(
            isVisible = state.isErrorDialogVisible,
            onDismissRequest = {
                onEvent(ProductDetailsEvent.DismissError)
            },
            text = state.errorMessage ?: "",
            confirmButtonOnClick = {
                onEvent(ProductDetailsEvent.FetchDetails)
            },
            dismissButtonOnClick = {
                onEvent(ProductDetailsEvent.DismissError)
            }
        )

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = """
                    id: ${state.product?.id}
                    name: ${state.product?.name}
                    description: ${state.product?.description}
                    clientCount: ${state.product?.clientCount}
                """.trimIndent()
            )
        }
    }
}