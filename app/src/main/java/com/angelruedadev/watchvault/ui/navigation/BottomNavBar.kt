package com.angelruedadev.watchvault.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.angelruedadev.watchvault.domain.model.NavItem
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.zIndex

@Composable
fun BottomNavBar(
    navItemList: List<NavItem>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {
        NavigationBar(
            containerColor = Color.Transparent,
            tonalElevation = 0.dp
        ) {
            navItemList.forEachIndexed { index, item ->
                val isSelected = index == selectedIndex
                val iconRes = if (isSelected) item.iconActiveRes else item.iconInactiveRes

                NavigationBarItem(
                    selected = isSelected,
                    onClick = { onItemSelected(index) },
                    icon = {
                        Icon(
                            painter = painterResource(id = iconRes),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp),
                            tint = Color.Unspecified
                        )
                    },
                    alwaysShowLabel = false,
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}