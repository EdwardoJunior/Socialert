package com.eduardo.socialert.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.eduardo.socialert.R
import kotlin.concurrent.timerTask

@Composable
fun CIcon(
    painter: Painter,
    contentDescription: String,
    tint : Color = Color(0XFF2C5FAA),
    height : Int = 20,
    rotate : Float = 0F,
    padding : Dp = 0.dp
) {
    Icon(
        painter = painter,
        contentDescription = contentDescription,
        modifier = Modifier.height(height.dp).rotate(rotate).padding(padding),
        tint = tint
    )
}


@Composable
fun CMenuIcon(
    painter: Painter,
    contentDescription: String,
){
    Icon(
        painter = painter,
        contentDescription = contentDescription,
        modifier = Modifier.height(20.dp)
    )
}