package com.angelruedadev.watchvault.domain.model

import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val route: String,
    val iconActiveRes: Int,
    val iconInactiveRes: Int
)