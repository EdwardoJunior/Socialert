package com.eduardo.socialert.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eduardo.socialert.R

@Composable
fun CFormHeader(
    title : String = stringResource(id = R.string.form_register_title),
    subtitle : String = ""
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "app_logo",
            modifier = Modifier
                .padding(top = 10.dp)
                .height(100.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xff2C5FAA)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = subtitle,
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xff2c5faa),
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}