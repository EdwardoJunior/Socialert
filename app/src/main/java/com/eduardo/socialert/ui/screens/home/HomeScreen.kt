package com.eduardo.socialert.ui.screens.home

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.eduardo.socialert.navigation.AppScreens
import com.eduardo.socialert.ui.viewmodel.auth.LoginViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    authViewModel: LoginViewModel,
    context: Context,
    token: String?
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "HOME")

        Button(onClick = {
            authViewModel.logoutUser(context)
        }) {
            Text("Cerrar sesión")
        }

        authViewModel.logoutResponse.value?.let {
            if (it.message == "Cierre de sesión exitoso") {
                Toast.makeText(context, "Cierre de sesión exitoso", Toast.LENGTH_LONG).show()

                navController.navigate(AppScreens.LoginScreen.route)}
        }
    }
}