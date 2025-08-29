package com.ad_coding.plannettestapp.ui.util.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ad_coding.plannettestapp.R
import com.ad_coding.plannettestapp.ui.screen.grade_list.GradeListEvent

@Composable
fun ErrorDialog(
    isVisible: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    title: String = stringResource(R.string.error_occurred),
    text: String,
    confirmButtonOnClick: () -> Unit = {},
    confirmButtonText: String = stringResource(R.string.retry),
    dismissButtonOnClick: () -> Unit = {},
    dismissButtonText: String = stringResource(R.string.dismiss)
) {
    AnimatedVisibility(visible = isVisible) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = {
                Text(text = title)
            },
            text = {
                Text(text = text)
            },
            confirmButton = {
                TextButton(
                    onClick = confirmButtonOnClick
                ) {
                    Text(text = confirmButtonText)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = dismissButtonOnClick
                ) {
                    Text(text = dismissButtonText)
                }
            },
            modifier = modifier
        )
    }
}