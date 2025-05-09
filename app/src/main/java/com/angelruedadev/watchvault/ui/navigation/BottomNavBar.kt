package com.angelruedadev.watchvault.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.angelruedadev.watchvault.domain.model.NavItem

@Composable
fun BottomNavBar(navItemList: List<NavItem>, selectedIndex: Int, onItemSelected: (Int) -> Unit) {

    NavigationBar {
        navItemList.forEachIndexed { index, navItem ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick ={
                    onItemSelected(index)
                },
                icon = { Icon(imageVector = navItem.icon, contentDescription = "Icon") },
                label= { Text(text = navItem.label) },
                )
        }
    }
}