package com.example.ktsproject_reptrack.presentation.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ktsproject_reptrack.presentation.screens.LoginScreen
import com.example.ktsproject_reptrack.presentation.screens.MainScreen
import com.example.ktsproject_reptrack.presentation.screens.StartScreen
import com.example.ktsproject_reptrack.presentation.viewmodel.LoginViewModel
import com.example.ktsproject_reptrack.presentation.viewmodel.MainViewModel
import com.example.ktsproject_reptrack.presentation.viewmodel.LoginUiEvent

sealed class Screen(val route: String) {
    data object Start : Screen("start")
    data object Login : Screen("login")
    data object Main : Screen("main")
}

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    onFinish: () -> Unit = {}
) {
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
            val viewModel: LoginViewModel = viewModel()

            LaunchedEffect(Unit) {
                viewModel.events.collect { event ->
                    when (event) {
                        is LoginUiEvent.LoginSuccess -> {
                            navController.navigate(Screen.Main.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        }
                    }
                }
            }

            LoginScreen(viewModel = viewModel)
        }

        composable(Screen.Main.route) {
            val viewModel: MainViewModel = viewModel()

            BackHandler {
                onFinish()
            }

            MainScreen(
                viewModel = viewModel,
                onBackClick = onFinish
            )
        }
    }
}
