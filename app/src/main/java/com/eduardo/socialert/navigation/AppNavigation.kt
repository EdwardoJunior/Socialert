package com.eduardo.socialert.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.eduardo.socialert.ui.screens.auth.register.RegisterPt1Screen
import com.eduardo.socialert.ui.screens.auth.register.RegisterPt2Screen
import com.eduardo.socialert.ui.screens.auth.login.LoginScreen
import com.eduardo.socialert.ui.viewmodel.auth.FormsInfoViewModel
import com.eduardo.socialert.ui.viewmodel.auth.LoginFormViewModel
import com.eduardo.socialert.ui.viewmodel.auth.RegisterViewModel
import com.eduardo.socialert.ui.viewmodel.home.HomeScreen

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    val formsInfoViewModel : FormsInfoViewModel = viewModel()
    val registerViewModel : RegisterViewModel = viewModel()
    val loginFormViewModel : LoginFormViewModel = viewModel()

    NavHost(navController = navController, startDestination = AppScreens.LoginScreen.route){

        composable(route = AppScreens.RegisterPt1Screen.route){
            RegisterPt1Screen(navController, formsInfoViewModel)
        }
        composable(route = AppScreens.RegisterPt2Screen.route){
            RegisterPt2Screen(navController, formsInfoViewModel, registerViewModel)
        }
        composable(route = AppScreens.LoginScreen.route){
            LoginScreen(navController, loginFormViewModel)
        }
        composable(route = AppScreens.HomeScreen.route){
            HomeScreen()
        }
    }
}