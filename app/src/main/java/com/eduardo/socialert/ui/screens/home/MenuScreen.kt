package com.eduardo.socialert.ui.screens.home

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.eduardo.socialert.R
import com.eduardo.socialert.navigation.AppScreens
import com.eduardo.socialert.navigation.MenuScreens
import com.eduardo.socialert.ui.components.CIcon
import com.eduardo.socialert.ui.components.CMenuIcon
import com.eduardo.socialert.ui.viewmodel.auth.LoginViewModel
import com.eduardo.socialert.ui.viewmodel.report.FormReportRegisterViewModel
import com.eduardo.socialert.ui.viewmodel.report.RegisterReportViewModel
import com.eduardo.socialert.ui.viewmodel.report.ReportViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(
    navController: NavController,
    authViewModel: LoginViewModel,
    context: Context,
    registerReportViewModel: RegisterReportViewModel,
    formReportRegisterViewModel: FormReportRegisterViewModel
) {

    val menuNavController = rememberNavController()
    var isExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row {
                        Text("SOCI")
                        Text("ALERT", color = Color(0XFF2C5FAA))
                    }
                },
                actions = {
                    Column(
                        modifier = Modifier.padding(end = 10.dp)
                    ) {
                        IconButton(
                            onClick = {
                                isExpanded = !isExpanded
                            }) {
                            CIcon(
                                painter = painterResource(id = R.drawable.options_menu_icon),
                                contentDescription = "menu_icon",
                                height = 25
                            )
                        }
                        DropdownMenu(
                            expanded = isExpanded,
                            onDismissRequest = { isExpanded = false }
                        ) {

                                DropdownMenuItem(
                                    text = { Text("Cerrar sesión") },
                                    trailingIcon = {
                                        if(authViewModel.isLoading.value){
                                            CircularProgressIndicator(color = Color(0XFF2C5FAA))
                                        }else{
                                            CIcon(
                                                painter = painterResource(id = R.drawable.logout_icon),
                                                contentDescription = "logout_icon"
                                            )
                                        }
                                    },
                                    onClick = {
                                        authViewModel.logoutUser(context)
                                    }
                                )
                            authViewModel.logoutResponse.value?.let {
                                if (it.message == "Cierre de sesión exitoso") {
                                    Toast.makeText(
                                        context,
                                        "Cierre de sesión exitoso",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    navController.navigate(AppScreens.LoginScreen.route)
                                }
                            }
                        }
                    }
                }
            )
        },
        bottomBar = { MenuBottomNavigation(menuNavController) },
//        floatingActionButton = {
//            Row(
//                verticalAlignment = Alignment.Bottom
//            ) {
//                FloatingActionButton(
//                    onClick = {navController.navigate(AppScreens.ReportRegisterScreen.route)},
//                    modifier = Modifier
//                        .height(60.dp)
//                        .width(60.dp),
//                    containerColor = Color(0XFF2C5FAA)
//                ) {
//                    CIcon(
//                        painter = painterResource(id = R.drawable.report_icon),
//                        contentDescription = "report_icon",
//                        tint = Color.White,
//                        height = 28,
//                        rotate = -20F
//                    )
//                }
//                Spacer(modifier = Modifier.width(15.dp))
//                FloatingActionButton(
//                    onClick = { /*TODO*/ },
//                    modifier = Modifier
//                        .height(70.dp)
//                        .width(70.dp),
//                    containerColor = Color(0XFF2C5FAA),
//                ) {
//                    CIcon(
//                        painter = painterResource(id = R.drawable.alert_icon),
//                        contentDescription = "alert_icon",
//                        tint = Color.White,
//                        height = 40
//                    )
//                }
//            }
//        },
//        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        NavHost(
            navController = menuNavController,
            startDestination = MenuScreens.HomeScreen.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = MenuScreens.HomeScreen.route) {
                HomeScreen(
                    navController,
                    context,
                    registerReportViewModel,
                    formReportRegisterViewModel
                )
//                NewHome(navController, context, registerReportViewModel, formReportRegisterViewModel)
            }
            composable(route = MenuScreens.ContactScreen.route) {
                ContactScreen()
            }
            composable(route = MenuScreens.LocationScreen.route) {
                LocationScreen()
            }
        }

    }


}

@Composable
fun MenuBottomNavigation(navController: NavController) {
    val menuScreens = listOf(
        MenuScreens.HomeScreen,
        MenuScreens.ContactScreen,
        MenuScreens.LocationScreen
    )

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 0.dp
    ) {
        val navBarStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBarStackEntry?.destination

        menuScreens.forEach { menuScreen ->
            NavigationBarItem(
                colors = NavigationBarItemColors(
                    selectedIconColor = Color(0XFF2C5FAA),
                    selectedTextColor = Color.Black,
                    selectedIndicatorColor = Color(0XFF96AFD5),
                    unselectedIconColor = Color.Black,
                    unselectedTextColor = Color.Black,
                    disabledIconColor = Color(0XFF2C5FAA),
                    disabledTextColor = Color.Black
                ),
                selected = currentRoute?.hierarchy?.any { it.route == menuScreen.route } == true,
                onClick = {
                    navController.navigate(menuScreen.route)
                    {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    CMenuIcon(
                        painter = painterResource(id = menuScreen.icon),
                        contentDescription = menuScreen.title
                    )
                },
                label = { Text(text = menuScreen.title) }
            )
        }

    }
}

