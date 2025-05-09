package com.angelruedadev.watchvault.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.angelruedadev.watchvault.ui.screens.MainContainer
import com.angelruedadev.watchvault.ui.screens.MovieScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "main") {
        composable("main") {
            MainContainer()
        }
    }
}