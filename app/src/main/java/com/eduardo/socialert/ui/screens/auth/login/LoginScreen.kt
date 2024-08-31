package com.eduardo.socialert.ui.screens.auth.login

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.eduardo.socialert.R
import com.eduardo.socialert.navigation.AppScreens
import com.eduardo.socialert.ui.components.CButton
import com.eduardo.socialert.ui.components.CFormHeader
import com.eduardo.socialert.ui.components.CLinkedText
import com.eduardo.socialert.ui.components.CTextError
import com.eduardo.socialert.ui.components.CTextField
import com.eduardo.socialert.ui.viewmodel.auth.LoginFormViewModel

@Composable
fun LoginScreen(navController: NavController, loginFormViewModel: LoginFormViewModel) {
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
        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
    ) {
        ContentHeader()
        ContentBody(navController, loginFormViewModel)
    }
}

@Composable
private fun ContentHeader() {
    CFormHeader(
        title = stringResource(id = R.string.form_login_title)
    )
}

@Composable
private fun ContentBody(navController: NavController, loginFormViewModel: LoginFormViewModel) {
    var showPassword by remember { mutableStateOf(false) }
    var showMessagesError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CTextField(
            value = loginFormViewModel.email,
            onValueChange = { loginFormViewModel.email = it },
            label = "Correo electrónico",
            isError = loginFormViewModel.emailError.isNotEmpty(),
            keyboardType = KeyboardType.Email
        )
        if (showMessagesError && !loginFormViewModel.validateLoginForm()) {
            CTextError(loginFormViewModel.emailError)
        }
        Spacer(Modifier.height(20.dp))
        CTextField(
            value = loginFormViewModel.password,
            onValueChange = { loginFormViewModel.password = it },
            label = "Contraseña",
            isError = loginFormViewModel.passwordError.isNotEmpty(),
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(
                        if (showPassword) {
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
            visualTransformation = if (!showPassword) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            keyboardType = KeyboardType.Password
        )
        if (showMessagesError) {
            CTextError(loginFormViewModel.passwordError)
        }
        Spacer(Modifier.height(40.dp))
        CButton(
            onClick = {
                if (loginFormViewModel.validateLoginForm()) {
                    navController.navigate(route = AppScreens.HomeScreen.route)
                } else {
                    showMessagesError = true
                }
            },
            text = "Ingresar"
        )

//        val registerResponse = registerViewModel.registerResponse.value
//        LaunchedEffect(registerResponse) {
//            registerResponse?.let {
//                if(it.status == 1){
//                    navController.navigate("login_screen?message=Registro%20exitoso")
//                }
//            }
//        }


        Spacer(Modifier.height(10.dp))
        CLinkedText(
            { navController.navigate(route = AppScreens.RegisterPt1Screen.route) },
            text = "¿Aun no tienes una cuenta?",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
