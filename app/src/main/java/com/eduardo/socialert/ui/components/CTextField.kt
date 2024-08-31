package com.eduardo.socialert.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import kotlin.math.sin

@Composable
fun CTextField(
    value: String,
    onValueChange: (String) -> Unit = {},
    label: String,
    trailingIcon:  @Composable() (() -> Unit)? = null,
    isError : Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None
){
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(label, color = Color(0x80444444))
        },
        trailingIcon = trailingIcon,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(capitalization = capitalization, keyboardType = keyboardType),
        singleLine = true,
        shape = RoundedCornerShape(20.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xffEAE1E1),
            focusedContainerColor = Color(0xffEAE1E1),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color(0XFF2C5FAA),
            focusedTextColor = Color(0xff444444),
            unfocusedTextColor = Color(0xff444444),
            errorIndicatorColor = Color.Transparent,
            errorContainerColor = Color(0xffEAE1E1),
            errorTextColor = Color.Red,
            errorSupportingTextColor = Color.Red,
            selectionColors = TextSelectionColors(handleColor = Color(0XFF2C5FAA), backgroundColor = Color(0X502C5FAA)),
            errorCursorColor = Color(0XFF2C5FAA)
        ),
    )
}