package com.angelruedadev.watchvault.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.angelruedadev.watchvault.R
import com.angelruedadev.watchvault.ui.navigation.AppScreens
import com.angelruedadev.watchvault.ui.navigation.BottomNavBar
import com.angelruedadev.watchvault.ui.navigation.NavItemList
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainContainer(navController: NavController) {
    val tabNavController = rememberNavController()
    val navItems = NavItemList.navItemList
    val currentDestination by tabNavController.currentBackStackEntryAsState()

    val selectedIndex = navItems.indexOfFirst { it.route == currentDestination?.destination?.route }
        .takeIf { it != -1 } ?: 0


    Scaffold(
        containerColor = colorResource(R.color.transparent),
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

    ) { _ ->
        Box(modifier = Modifier.fillMaxSize()){
            NavHost(
                navController = tabNavController,
                startDestination = AppScreens.MovieTab.route,
                modifier = Modifier.zIndex(0f)
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
            val gradientBrush = Brush.verticalGradient(
                colorStops = arrayOf(
                    0.0f to colorResource(R.color.dark_blue).copy(alpha = 0f),
                    0.5f to colorResource(R.color.dark_blue).copy(alpha = 0.75f),
                    1.0f to colorResource(R.color.dark_blue).copy(alpha = 1f)
                )
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .align(Alignment.BottomCenter)
                    .background(brush = gradientBrush)
                    .zIndex(1f)
            )
        }
    }
}