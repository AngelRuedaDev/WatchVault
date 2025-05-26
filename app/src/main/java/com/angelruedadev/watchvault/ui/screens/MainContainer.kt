package com.angelruedadev.watchvault.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.angelruedadev.watchvault.ui.navigation.AppScreens
import com.angelruedadev.watchvault.ui.navigation.BottomNavBar
import com.angelruedadev.watchvault.ui.navigation.NavItemList


@Composable
fun MainContainer(navController: NavController) {
    val tabNavController = rememberNavController()
    val navItems = NavItemList.navItemList
    val currentDestination by tabNavController.currentBackStackEntryAsState()

    val selectedIndex = navItems.indexOfFirst { it.route == currentDestination?.destination?.route }
        .takeIf { it != -1 } ?: 0

    Scaffold(
        bottomBar = {
            BottomNavBar(
                navItemList = navItems,
                selectedIndex = selectedIndex,
                onItemSelected = {index ->
                    val route = navItems[index].route
                    tabNavController.navigate(route){
                        popUpTo(AppScreens.MovieTab.route) {saveState = true}
                        launchSingleTop  = true
                        restoreState = true
                    }
                })
        }
    ) { innerPadding ->

        NavHost(
            navController = tabNavController,
            startDestination = AppScreens.MovieTab.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(AppScreens.MovieTab.route) {
                MovieScreen(navController = navController)
            }
            composable(AppScreens.ShowTab.route) {
                ShowsScreen(navController = navController)
            }
            composable(AppScreens.UserTab.route) {
                UserScreen(navController = navController)
            }
        }

    }
}