package com.angelruedadev.watchvault.ui.navigation

sealed class AppScreens(val route: String) {
    data object MainContainer: AppScreens("main_container_screen")
    data object DetailScreen: AppScreens("detail_screen")
}