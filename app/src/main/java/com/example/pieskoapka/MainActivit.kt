package com.example.pieskoapka

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.pieskoapka.ui.theme.PieskoApkaTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pieskoapka.ui.details.DogDetailsScreen
import com.example.pieskoapka.ui.dogs.DogListApp
import dagger.hilt.android.AndroidEntryPoint

data class Dog(val name: String, val breed: String, var isFavorite: Boolean = false)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PieskoApkaTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "Screen_A", builder = {
                    composable("Screen_A") {
                        DogListApp(navController)
                    }
                    composable(Routes.ScreenB+"/{dogName}/{breed}"){
                        val dogName = it.arguments?.getString("dogName")
                        val breed = it.arguments?.getString("breed")
                        DogDetailsScreen(navController, dogName?:"No name", breed?: "No breed")
                    }
                    composable("Setings"){
                        SettingsScreen(navController)
                    }
                    composable("Profile"){
                        ProfileScreen(navController)
                    }
                })
            }
        }
    }
}



