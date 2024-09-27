package com.eduardo.socialert.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
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
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    readOnly : Boolean = false,
    label: String,
    trailingIcon:  @Composable() (() -> Unit)? = null,
    isError : Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine : Boolean = true,
    maxLines : Int = 1,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None
){
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        readOnly = readOnly,
        label = {
            Text(label, color = MaterialTheme.colorScheme.onPrimaryContainer)
        },
        trailingIcon = trailingIcon,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(capitalization = capitalization, keyboardType = keyboardType),
        singleLine = singleLine,
        maxLines = maxLines,
        shape = RoundedCornerShape(20.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.primary,
//            focusedTextColor = Color(0xff444444),
//            unfocusedTextColor = Color(0xff444444),
            errorIndicatorColor = Color.Transparent,
            errorContainerColor = MaterialTheme.colorScheme.primaryContainer,
            errorTextColor = Color.Red,
            errorSupportingTextColor = Color.Red,
            selectionColors = TextSelectionColors(MaterialTheme.colorScheme.primary, backgroundColor = MaterialTheme.colorScheme.secondary),
            errorCursorColor = MaterialTheme.colorScheme.primary
        ),
    )
}