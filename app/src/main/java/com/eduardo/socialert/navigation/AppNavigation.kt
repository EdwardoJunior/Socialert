package com.eduardo.socialert.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eduardo.socialert.ui.screens.auth.register.RegisterPt1Screen
import com.eduardo.socialert.ui.screens.auth.register.RegisterPt2Screen
import com.eduardo.socialert.ui.screens.auth.login.LoginScreen
import com.eduardo.socialert.ui.viewmodel.auth.FormsInfoViewModel
import com.eduardo.socialert.ui.viewmodel.auth.LoginFormViewModel
import com.eduardo.socialert.ui.viewmodel.auth.LoginViewModel
import com.eduardo.socialert.ui.viewmodel.auth.RegisterViewModel
import com.eduardo.socialert.ui.screens.home.MenuScreen
import com.eduardo.socialert.ui.screens.home.reports.ReportRegisterScreen
import com.eduardo.socialert.ui.viewmodel.report.FormReportRegisterViewModel
import com.eduardo.socialert.ui.viewmodel.report.RegisterReportViewModel
import com.eduardo.socialert.ui.viewmodel.report.ReportViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val formsInfoViewModel: FormsInfoViewModel = viewModel()
    val registerViewModel: RegisterViewModel = viewModel()
    val loginFormViewModel: LoginFormViewModel = viewModel()
    val authViewModel: LoginViewModel = viewModel()
    val reportViewModel: ReportViewModel = viewModel()
    val formReportRegisterViewModel: FormReportRegisterViewModel = viewModel()
    val registerReportViewModel: RegisterReportViewModel = viewModel()

    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("MyApp", Context.MODE_PRIVATE)
    val token = sharedPref.getString("auth_token", null)

    NavHost(
        navController = navController,

        startDestination = if (token != null) {
            AppScreens.MenuScreen.route
        } else {
            AppScreens.LoginScreen.route
        }
    ) {

        composable(route = AppScreens.RegisterPt1Screen.route) {
            RegisterPt1Screen(navController, formsInfoViewModel)
        }
        composable(route = AppScreens.RegisterPt2Screen.route) {
            RegisterPt2Screen(navController, formsInfoViewModel, registerViewModel)
        }
        composable(route = AppScreens.LoginScreen.route) {
            LoginScreen(navController, loginFormViewModel, authViewModel, context)
        }
        composable(route = AppScreens.MenuScreen.route) {
            MenuScreen(navController, authViewModel, context, registerReportViewModel, formReportRegisterViewModel)
        }
        composable(route = AppScreens.ReportRegisterScreen.route) {
            ReportRegisterScreen(
                navController,
                context,
                reportViewModel,
                formReportRegisterViewModel,
                registerReportViewModel
            )
        }
    }
}


