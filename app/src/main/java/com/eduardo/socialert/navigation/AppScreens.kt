package com.eduardo.socialert.navigation

sealed class AppScreens(val route : String) {
    object RegisterPt1Screen : AppScreens("register_pt1_screen")
    object RegisterPt2Screen : AppScreens("register_pt2_screen")
    //object LoginScreen : AppScreens("login_screen?message={message}")
    object LoginScreen : AppScreens("login_screen")
    object HomeScreen : AppScreens("home_screen")
}