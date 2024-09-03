package com.eduardo.socialert.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun CIcon(
    painter: Painter,
    contentDescription: String
) {
    Icon(
        painter = painter,
        contentDescription = contentDescription,
        modifier = Modifier.height(20.dp),
        tint = Color(0XFF2C5FAA)
    )
}