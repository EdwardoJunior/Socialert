package com.eduardo.socialert.ui.screens.auth.register

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.eduardo.socialert.R
import com.eduardo.socialert.data.model.request.UserRequest
import com.eduardo.socialert.navigation.AppScreens
import com.eduardo.socialert.ui.components.CButton
import com.eduardo.socialert.ui.components.CFormHeader
import com.eduardo.socialert.ui.components.CLinkedText
import com.eduardo.socialert.ui.components.CTextError
import com.eduardo.socialert.ui.components.CTextField
import com.eduardo.socialert.ui.components.CTextWarning
import com.eduardo.socialert.ui.viewmodel.auth.FormsInfoViewModel
import com.eduardo.socialert.ui.viewmodel.auth.RegisterViewModel

@Composable
fun RegisterPt2Screen(
    navController: NavController,
    formsInfoViewModel: FormsInfoViewModel,
    registerViewModel: RegisterViewModel
) {
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
        ContentBody(navController, formsInfoViewModel, registerViewModel)
    }
}

@Composable
private fun ContentHeader() {
    CFormHeader(subtitle = stringResource(id = R.string.form_user_data_title))
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
private fun ContentBody(
    navController: NavController,
    formsInfoViewModel: FormsInfoViewModel,
    registerViewModel: RegisterViewModel
) {
    var showPassword by remember { mutableStateOf(false) }
    var showPasswordRepeat by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CTextField(
            value = formsInfoViewModel.email,
            onValueChange = { formsInfoViewModel.email = it },
            label = stringResource(id = R.string.email_label),
            isError = formsInfoViewModel.emailError.isNotEmpty(),
            keyboardType = KeyboardType.Email
        )
        if (formsInfoViewModel.emailError.isNotEmpty()) {
            CTextError(formsInfoViewModel.emailError)
        }
        Spacer(Modifier.height(20.dp))

        CTextField(
            value = formsInfoViewModel.password,
            onValueChange = { formsInfoViewModel.password = it },
            label = stringResource(id = R.string.password_label),
            isError = formsInfoViewModel.passwordError.isNotEmpty(),
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(
                        if (showPassword) {
                            painterResource(id = R.drawable.eye_slash_regular)
                        } else {
                            painterResource(id = R.drawable.eye_regular)
                        },
                        contentDescription = "show_password",
                        modifier = Modifier.height(20.dp),
                        tint = Color(0XFF2C5FAA)
                    )
                }
            },
            visualTransformation = if (!showPassword) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            keyboardType = KeyboardType.Password
        )
        if (formsInfoViewModel.passwordError.isNotEmpty()) {
            CTextError(formsInfoViewModel.passwordError)
        }
        Spacer(Modifier.height(20.dp))

        CTextField(
            value = formsInfoViewModel.repeatPassword,
            onValueChange = { formsInfoViewModel.repeatPassword = it },
            label = stringResource(id = R.string.repeat_password_label),
            isError = formsInfoViewModel.repeatPasswordError.isNotEmpty(),
            trailingIcon = {
                IconButton(onClick = { showPasswordRepeat = !showPasswordRepeat }) {
                    Icon(
                        if (showPasswordRepeat) {
                            painterResource(id = R.drawable.eye_slash_regular)
                        } else {
                            painterResource(id = R.drawable.eye_regular)
                        },
                        contentDescription = "",
                        modifier = Modifier.height(20.dp),
                        tint = Color(0XFF2C5FAA)
                    )
                }
            },
            visualTransformation = if (!showPasswordRepeat) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            keyboardType = KeyboardType.Password
        )
        if (formsInfoViewModel.repeatPasswordError.isNotEmpty()) {
            CTextError(errorText = formsInfoViewModel.repeatPasswordError)
        }
        Spacer(Modifier.height(30.dp))

        val isFormValid = formsInfoViewModel.validateRegisterFormPt2() &&
                formsInfoViewModel.email.isNotBlank() &&
                formsInfoViewModel.password.isNotBlank() &&
                formsInfoViewModel.repeatPassword.isNotBlank()

        if (!isFormValid) {
            CTextWarning(warningText = stringResource(id = R.string.validate_empty_fields))
        }
        Spacer(Modifier.height(10.dp))

        val isLoading = registerViewModel.isLoading.value

        if (isLoading){
            CircularProgressIndicator()
        }else {
            CButton(
                onClick = {
                    val userRequest = UserRequest(
                        email = formsInfoViewModel.email,
                        password = formsInfoViewModel.password,
                        name = formsInfoViewModel.name,
                        lastname = formsInfoViewModel.lastname,
                        phone = formsInfoViewModel.phoneNumber,
                        curp = formsInfoViewModel.curp,
                        gender = formsInfoViewModel.gender
                    )

                    registerViewModel.registerUser(userRequest)
                },
                enabled = isFormValid,
                text = stringResource(id = R.string.register_button_text)
            )
        }

        val message = registerViewModel.registerResponse.value?.message

        LaunchedEffect (message, isLoading){
            if(!isLoading && message == "Usuario creado exitosamente."){
                navController.navigate(route = AppScreens.LoginScreen.route)
                formsInfoViewModel.cleanFields()
            }
        }


        val context = LocalContext.current
        val registerResponse by rememberUpdatedState(registerViewModel.registerResponse.value)
        LaunchedEffect(registerResponse) {
            registerResponse?.let { response ->
                if (response.message.isNotEmpty()) {
                    Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()
                }
            }
        }
        registerViewModel.error.value.let { errorMessage ->
            if (errorMessage.isNotBlank()) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
        }

        Spacer(Modifier.height(10.dp))
        CLinkedText(
            { navController.navigate(route = AppScreens.RegisterPt1Screen.route) },
            text = stringResource(id = R.string.go_back_link),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
