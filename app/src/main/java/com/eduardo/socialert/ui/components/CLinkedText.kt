package com.eduardo.socialert.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun CLinkedText(
    onClick : () -> Unit,
    text : String,
    style : TextStyle
){
    Text(
        text,
        color = Color(0xff2C5FAA),
        modifier = Modifier
            .clickable(onClick = onClick),
        style = style
    )
}