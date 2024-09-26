package com.eduardo.socialert.navigation

import androidx.compose.ui.res.painterResource
import com.eduardo.socialert.R
import com.eduardo.socialert.ui.components.CIcon

sealed class AppScreens(val route : String) {
    object RegisterPt1Screen : AppScreens("register_pt1_screen")
    object RegisterPt2Screen : AppScreens("register_pt2_screen")
    object LoginScreen : AppScreens("login_screen")
    data object MenuScreen : AppScreens("menu_screen")

    //Reports
    data object ReportRegisterScreen : AppScreens("report_register_screen")
}


sealed class MenuScreens(val route : String, val title : String, val icon : Int){
    data object HomeScreen : MenuScreens("home_screen", "Inicio", R.drawable.home_icon)
    data object ContactScreen : MenuScreens("contact_screen", "Contactos", R.drawable.contact_icon)
    data object LocationScreen : MenuScreens("location_screen", "Ubicación", R.drawable.location_icon)
}