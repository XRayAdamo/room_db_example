package com.rayadams.roomdbexample.views

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.rayadams.roomdbexample.R


@Composable
fun AskDeleteDialog(onCancel: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(onDismissRequest = { onCancel() },
        title = { Text(stringResource(R.string.txt_delete_rec)) },
        text = { Text(stringResource(R.string.txt_confirm_delete_rec)) },
        dismissButton = {
            TextButton(
                onClick = {
                    onCancel()
                },
            ) {
                Text(stringResource(R.string.txt_cancel), fontWeight = FontWeight.W500)
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm()
                },
            ) {
                Text(
                    "Delete",
                    fontWeight = FontWeight.W500,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    )
}
