package com.angelruedadev.watchvault.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.PlayArrow
import com.angelruedadev.watchvault.domain.model.NavItem

object NavItemList {
    val navItemList = listOf(
        NavItem("Movies", Icons.Default.PlayArrow),
        NavItem("TV Shows", Icons.Default.DateRange),
        NavItem("User", Icons.Default.AccountCircle)
    )
}