package com.example.marvel.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.marvel.screens.information.screen.InfoScreen
import com.example.marvel.screens.main.screen.MainScreen


@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(Screen.MainScreen.route) {
            MainScreen(navController)
        }
        composable(
            Screen.InfoScreen.route + "/{heroId}",
            arguments = listOf(navArgument("heroId") { type = NavType.IntType }),
            deepLinks = listOf(navDeepLink {
                uriPattern = "deeplink://app.com/{heroId}"
            })

        ) {
            InfoScreen(navController)
        }
    }
}

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object InfoScreen : Screen("info_screen")
}
