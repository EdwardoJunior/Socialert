package com.eduardo.socialert.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CButton(onClick: () -> Unit, text:String, enabled: Boolean = true){
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(200.dp)
            .height(46.dp),
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xff2C5FAA),
            contentColor = Color.White,
            disabledContainerColor = Color(0x9f2C5FAA),
            disabledContentColor = Color.White
        )
    ) {
        Text(text, style = MaterialTheme.typography.bodyMedium)
    }
    Spacer(Modifier.height(20.dp))
}