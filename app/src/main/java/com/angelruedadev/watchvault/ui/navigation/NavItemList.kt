package com.angelruedadev.watchvault.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.PlayArrow
import com.angelruedadev.watchvault.R
import com.angelruedadev.watchvault.domain.model.NavItem

object NavItemList {
    val navItemList = listOf(
        NavItem(
            route = AppScreens.MovieTab.route,
            iconActiveRes = R.drawable.ic_movies_active,
            iconInactiveRes = R.drawable.ic_movies_inactive
        ),
        NavItem(
            route = AppScreens.ShowTab.route,
            iconActiveRes = R.drawable.ic_tvseries_active,
            iconInactiveRes = R.drawable.ic_tvseries_inactive
        ),
        NavItem(
            route = AppScreens.UserTab.route,
            iconActiveRes = R.drawable.ic_user_active,
            iconInactiveRes = R.drawable.ic_user_inactive
        )
    )
}