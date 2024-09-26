package com.eduardo.socialert.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun CButton(
    onClick: () -> Unit,
    text: String,
    enabled: Boolean = true,
    width: Int = 200,
    cornerShape: Int = 12,
    containerColor: Color = Color(0XFF2C5FAA),
    contentColor: Color = Color.White,
    disableContainerColor : Color = Color(0X9F2C5FAA)
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(width.dp)
            .height(46.dp),
        enabled = enabled,
        shape = RoundedCornerShape(cornerShape.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = disableContainerColor,
            disabledContentColor = Color.White
        )
    ) {
        Text(text, style = MaterialTheme.typography.bodyMedium)
    }
    Spacer(Modifier.height(20.dp))
}
