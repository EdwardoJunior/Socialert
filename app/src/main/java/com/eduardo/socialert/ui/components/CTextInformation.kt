package com.eduardo.socialert.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CTextError(errorText: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 7.dp),
    ) {
        Text(
            errorText,
            color = Color(0xFFFF1631),
            style = MaterialTheme.typography.bodySmall,
        )
    }

}

@Composable
fun CTextWarning(warningText: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 7.dp),
    ) {
        Text(
            warningText,
            color = Color(0xFFFF9800),
            style = MaterialTheme.typography.bodySmall,
        )
    }

}