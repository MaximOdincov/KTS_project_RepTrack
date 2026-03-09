package com.example.ktsproject_reptrack.presentation.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ktsproject_reptrack.presentation.screens.LoginScreen
import com.example.ktsproject_reptrack.presentation.screens.MainScreen
import com.example.ktsproject_reptrack.presentation.screens.NutritionScreen
import com.example.ktsproject_reptrack.presentation.screens.StartScreen
import com.example.ktsproject_reptrack.presentation.viewmodel.LoginViewModel
import com.example.ktsproject_reptrack.presentation.viewmodel.MainViewModel
import com.example.ktsproject_reptrack.presentation.viewmodel.NutritionViewModel

sealed class Screen(val route: String) {
    data object Start : Screen("start")
    data object Login : Screen("login")
    data object Main : Screen("main")
    data object Nutrition : Screen("nutrition")
}

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    onFinish: () -> Unit = {}
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Start.route,
        enterTransition = { androidx.compose.animation.EnterTransition.None },
        exitTransition = { androidx.compose.animation.ExitTransition.None },
        modifier = Modifier.safeDrawingPadding()
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

            LoginScreen(
                viewModel = viewModel,
                navController = navController,
                onFinish = onFinish
            )
        }

        composable(Screen.Main.route) {
            val viewModel: MainViewModel = viewModel()

            BackHandler {
                onFinish()
            }

            MainScreen(
                viewModel = viewModel,
                onBackClick = onFinish,
                onNavigateToNutrition = {
                    navController.navigate(Screen.Nutrition.route)
                }
            )
        }

        composable(Screen.Nutrition.route) {
            val viewModel: NutritionViewModel = viewModel()

            BackHandler {
                navController.popBackStack()
            }

            NutritionScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() },
                onNavigateToMain = {
                    navController.popBackStack()
                }
            )
        }
    }
}
