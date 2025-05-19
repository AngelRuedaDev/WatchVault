package com.angelruedadev.watchvault.ui.navigation

sealed class AppScreens(val route: String) {
    data object MainContainer: AppScreens("main_container_screen")
    data object MovieDetailScreen: AppScreens("movie_detail_screen")
    data object TvShowDetailScreen: AppScreens("tv_show_detail_screen")
}