package com.example.ktsproject_reptrack.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ktsproject_reptrack.presentation.screens.LoginScreen
import com.example.ktsproject_reptrack.presentation.screens.StartScreen

sealed class Screen(val route: String) {
    data object Start : Screen("start")
    data object Login : Screen("login")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Start.route
    ) {
        composable(Screen.Start.route) {
            StartScreen(
                onContinueClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Start.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginClick = {
                }
            )
        }
    }
}
