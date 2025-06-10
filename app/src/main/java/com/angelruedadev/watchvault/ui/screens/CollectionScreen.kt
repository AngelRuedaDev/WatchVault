package com.angelruedadev.watchvault.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.angelruedadev.watchvault.data.local.entity.CollectionItemData
import com.angelruedadev.watchvault.data.local.entity.MovieEntity
import com.angelruedadev.watchvault.data.local.entity.TvShowEntity
import com.angelruedadev.watchvault.ui.navigation.AppScreens
import com.angelruedadev.watchvault.ui.screens.components.TitleSectionCollection
import com.angelruedadev.watchvault.ui.viewModels.CollectionViewModel

@Composable
fun CollectionScreen(navController: NavController, viewModel: CollectionViewModel = hiltViewModel()) {
    val collection = viewModel.collectionItems.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val error = viewModel.error.collectAsState()

    val collectionName = viewModel.savedStateHandle.get<String>("collectionName") ?: ""

    Box(modifier = Modifier.fillMaxSize()) {
        Column {

            TitleSectionCollection(
                title = collectionName,
                onSortSelected = { sortType ->
                    viewModel.sortList(sortType)
                }
            )

            LazyColumn(
                contentPadding = PaddingValues(16.dp)
            ) {
                items(collection.value) { collectionItem ->
                    CollectionItem(collectionItem) {
                        if (collectionName.contains("Movies", ignoreCase = true)) {
                            navController.navigate(AppScreens.MovieDetailScreen.route + "/${collectionItem.id}")
                        } else {
                            navController.navigate(AppScreens.TvShowDetailScreen.route + "/${collectionItem.id}")
                        }
                    }
                }

                // Muestra loader al final
                if (isLoading.value) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }

            if (collection.value.isEmpty() && !isLoading.value) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Build,
                            contentDescription = "No movies",
                            modifier = Modifier.size(72.dp),
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "There are no movies to show!",
                            color = Color.Red,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
            // Show error
            if (error.value != null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Error: ${error.value}",
                        color = Color.Red
                    )
                }
            }
        }
    }
}

@Composable
fun CollectionItem(collectionItem: CollectionItemData, onClick: () -> Unit) {

    val painter = rememberAsyncImagePainter(
        model = "https://image.tmdb.org/t/p/w185${collectionItem.photoPath}"
    )
    val imageState = painter.state

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardColors(
            contentColor = Color.Black,
            containerColor = Color.White,
            disabledContainerColor = Color.Red,
            disabledContentColor = Color.Red
        )
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (imageState is AsyncImagePainter.State.Loading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                }

                Image(
                    painter = painter,
                    contentDescription = collectionItem.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = collectionItem.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

