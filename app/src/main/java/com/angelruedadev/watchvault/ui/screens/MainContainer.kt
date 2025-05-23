package com.angelruedadev.watchvault.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.angelruedadev.watchvault.ui.navigation.BottomNavBar
import com.angelruedadev.watchvault.ui.navigation.NavItemList

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainContainer(navController: NavController) {
    var selectedIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        bottomBar = {
            BottomNavBar(
                navItemList = NavItemList.navItemList,
                selectedIndex = selectedIndex,
                onItemSelected = { index -> selectedIndex = index })
        }
    ) {
        ContentScreen(selectedIndex, navController)
    }
}

@Composable
fun ContentScreen(selectedIndex: Int, navController: NavController) {
    when(selectedIndex){
        0 -> MovieScreen(navController = navController)
        1 -> ShowsScreen(navController = navController)
        2 -> UserScreen()
    }
}
