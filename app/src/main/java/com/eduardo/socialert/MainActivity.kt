package com.eduardo.socialert

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.eduardo.socialert.navigation.AppNavigation
import com.eduardo.socialert.ui.theme.SocialertTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
                val darkTheme = isSystemInDarkTheme()

                SocialertTheme(darkTheme, false){
                    Surface (
                        color = if(darkTheme) Color(0XFF050505) else Color.White
                    ){
                        AppNavigation()
                    }
                }

        }
    }
}
