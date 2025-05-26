package com.angelruedadev.watchvault.ui.navigation

sealed class AppScreens(val route: String) {
    data object MainContainer : AppScreens("main_container_screen")
    data object MovieTab : AppScreens("movie_tab")
    data object ShowTab : AppScreens("show_tab")
    data object UserTab : AppScreens("user_tab")
    data object MovieDetailScreen : AppScreens("movie_detail_screen")
    data object TvShowDetailScreen : AppScreens("tv_show_detail_screen")
    data object CollectionScreen : AppScreens("collection_screen")
}