package com.angelruedadev.watchvault.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.angelruedadev.watchvault.ui.screens.CollectionScreen
import com.angelruedadev.watchvault.ui.screens.MainContainer
import com.angelruedadev.watchvault.ui.screens.MovieDetailScreen
import com.angelruedadev.watchvault.ui.screens.TvShowDetailScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreens.MainContainer.route) {
        composable(AppScreens.MainContainer.route) {
            MainContainer(navController)
        }

        composable(route = AppScreens.MovieDetailScreen.route + "/{id}",
            arguments = listOf(navArgument(name = "id"){type = NavType.IntType}))
        { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 1
            MovieDetailScreen(id = id)
        }

        composable(route = AppScreens.TvShowDetailScreen.route + "/{id}",
            arguments = listOf(navArgument(name = "id"){type = NavType.IntType}))
        { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 1
            TvShowDetailScreen(id = id)
        }

        composable(route = AppScreens.CollectionScreen.route + "/{collectionName}",
            arguments = listOf(navArgument(name = "collectionName"){type = NavType.StringType}))
        {
            CollectionScreen(navController = navController)
        }
    }
}