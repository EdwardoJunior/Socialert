package com.eduardo.socialert.ui.screens.auth.register

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.eduardo.socialert.R
import com.eduardo.socialert.navigation.AppScreens
import com.eduardo.socialert.ui.components.CButton
import com.eduardo.socialert.ui.components.CFormHeader
import com.eduardo.socialert.ui.components.CLinkedText
import com.eduardo.socialert.ui.components.CTextError
import com.eduardo.socialert.ui.components.CTextField
import com.eduardo.socialert.ui.components.CTextWarning
import com.eduardo.socialert.ui.viewmodel.auth.FormsInfoViewModel

@Composable
fun RegisterPt1Screen(navController: NavController, formsInfoViewModel: FormsInfoViewModel) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    keyboardController?.hide()
                })
            }
            .padding(40.dp)
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ContentHeader()
        ContentBody(navController, formsInfoViewModel)
    }
}

@Composable
private fun ContentHeader() {
    CFormHeader(subtitle = stringResource(id = R.string.form_personal_data_title))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ContentBody(navController: NavController, formsInfoViewModel: FormsInfoViewModel) {

    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CTextField(
            value = formsInfoViewModel.name,
            onValueChange = { formsInfoViewModel.name = it },
            label = stringResource(id = R.string.name_label),
            isError = formsInfoViewModel.nameError.isNotEmpty(),
            keyboardType = KeyboardType.Text,
            capitalization = KeyboardCapitalization.Words

        )
        if (formsInfoViewModel.nameError.isNotEmpty()) {
            CTextError(formsInfoViewModel.nameError)
        }
        Spacer(Modifier.height(20.dp))

        CTextField(
            value = formsInfoViewModel.lastname,
            onValueChange = { formsInfoViewModel.lastname = it },
            label = stringResource(id = R.string.lastname_label),
            isError = formsInfoViewModel.lastNameError.isNotEmpty(),
            keyboardType = KeyboardType.Text,
            capitalization = KeyboardCapitalization.Words
        )
        if (formsInfoViewModel.lastNameError.isNotEmpty()) {
            CTextError(formsInfoViewModel.lastNameError)
        }
        Spacer(Modifier.height(20.dp))

        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded },
        ) {
            CTextField( value = when (formsInfoViewModel.gender) {
                "M" -> "Masculino"
                "F" -> "Femenino"
                "N" -> "Prefiero no decirlo"
                else -> ""
            },
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.menuAnchor().padding(bottom = 10.dp),
                label = "Género",
                isError = formsInfoViewModel.genderError.isNotEmpty(),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
            )
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false},
                modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
            ) {
                formsInfoViewModel.genderOptions.forEachIndexed{index, selectedOption ->
                    if(index != 0) {
                        DropdownMenuItem(
                            text = { Text(selectedOption) },
                            onClick = {
                                when (selectedOption){
                                    "Masculino" -> formsInfoViewModel.gender = "M"
                                    "Femenino" -> formsInfoViewModel.gender = "F"
                                    "Prefiero no decirlo" -> formsInfoViewModel.gender = "N"
                                }
                                isExpanded = false
                            }
                        )
                    }
                }
            }
        }
        if (formsInfoViewModel.genderError.isNotEmpty()) {
            CTextError(formsInfoViewModel.genderError)
        }
        Spacer(Modifier.height(10.dp))

        CTextField(
            value = formsInfoViewModel.phoneNumber,
            onValueChange = { formsInfoViewModel.phoneNumber = it },
            label = stringResource(id = R.string.phoneNumber_label),
            isError = formsInfoViewModel.phoneNumberError.isNotEmpty(),
            keyboardType = KeyboardType.Number
        )
        if (formsInfoViewModel.phoneNumberError.isNotEmpty()) {
            CTextError(formsInfoViewModel.phoneNumberError)
        }
        Spacer(Modifier.height(20.dp))

        CTextField(
            value = formsInfoViewModel.curp,
            onValueChange = { formsInfoViewModel.curp = it.uppercase() },
            label = stringResource(id = R.string.curp_label),
            isError = formsInfoViewModel.curpError.isNotEmpty(),
            keyboardType = KeyboardType.Password,
        )
        if (formsInfoViewModel.curpError.isNotEmpty()) {
            CTextError(formsInfoViewModel.curpError)
        }
        Spacer(Modifier.height(30.dp))

        val isFormValid = formsInfoViewModel.validateRegisterFormPt1() &&
                formsInfoViewModel.name.isNotBlank() &&
                formsInfoViewModel.lastname.isNotBlank() &&
                formsInfoViewModel.phoneNumber.isNotBlank() &&
                formsInfoViewModel.curp.isNotBlank()

        if (!isFormValid) {
            CTextWarning(warningText = "Por favor llena todos los campos correctamente.")
        }
        Spacer(Modifier.height(10.dp))

        CButton(
            onClick = {
                navController.navigate(route = AppScreens.RegisterPt2Screen.route)
            },
            enabled = isFormValid,
            text = stringResource(id = R.string.next_button_text),
        )

        Spacer(Modifier.height(10.dp))
        CLinkedText(
            {
                navController.navigate(route = AppScreens.LoginScreen.route)
                formsInfoViewModel.cleanFields()
            },
            text = stringResource(id = R.string.go_to_login_link),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}


