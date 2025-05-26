package com.angelruedadev.watchvault.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.angelruedadev.watchvault.ui.navigation.AppScreens

@Composable
fun UserScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(WindowInsets.systemBars.only(WindowInsetsSides.Top).asPaddingValues())
    ){
        Column(){
            CollectionSection("Movies", navController)
            Spacer(Modifier.padding(25.dp))
            CollectionSection("TvShows", navController)
        }
    }
}

@Composable
fun CollectionSection(title: String, navController: NavController) {
    Column(modifier = Modifier.fillMaxWidth()){
        Text("$title lists: ")

        Row(modifier = Modifier.fillMaxWidth()) {
            Card(modifier = Modifier.weight(1f).padding(5.dp).clickable {
                navController.navigate(route = AppScreens.CollectionScreen.route + "/${title}Likes")
            }) {
                Icon(Icons.Filled.Favorite, contentDescription = "Liked collection", modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 5.dp))
                Text("Likes", modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 5.dp))
            }
            Card(modifier = Modifier.weight(1f).padding(5.dp).clickable {
                navController.navigate(route = AppScreens.CollectionScreen.route + "/${title}Watchlist")
            }) {
                Icon(Icons.Filled.AddCircle, contentDescription = "WatchList collection", modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 5.dp))
                Text("Watchlist", modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 5.dp))
            }
            Card(modifier = Modifier.weight(1f).padding(5.dp).clickable {
                navController.navigate(route = AppScreens.CollectionScreen.route + "/${title}Rated")
            }) {
                Icon(Icons.Filled.Star, contentDescription = "WatchList collection", modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 5.dp))
                Text("Rated", modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 5.dp))
            }
        }
    }
}
