package com.example.marvel.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.marvel.screens.information.screen.InfoScreen
import com.example.marvel.screens.main.screen.MainScreen
import com.example.marvel.screens.main.screen.MainViewModel



@Composable
fun AppNavHost() {
    val mainViewModel: MainViewModel = viewModel()
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(Screen.MainScreen.route) {
            MainScreen(navController, mainViewModel = mainViewModel)
        }
        composable(
            Screen.InfoScreen.route + "/{heroId}",
            arguments = listOf(navArgument("heroId") { type = NavType.IntType })
        ) { backStackEntity ->
            InfoScreen(
                navController,
                backStackEntity.arguments?.getInt("heroId"),
                mainViewModel = mainViewModel
            )
        }
    }
}

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object InfoScreen : Screen("info_screen")
}
