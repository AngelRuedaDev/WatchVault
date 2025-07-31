package com.angelruedadev.watchvault.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
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
import com.angelruedadev.watchvault.ui.navigation.AppScreens
import com.angelruedadev.watchvault.ui.screens.components.TitleSectionCollection
import com.angelruedadev.watchvault.ui.viewModels.CollectionViewModel
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.angelruedadev.watchvault.R

@Composable
fun CollectionScreen(navController: NavController, viewModel: CollectionViewModel = hiltViewModel()) {
    val collection = viewModel.collectionItems.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val error = viewModel.error.collectAsState()

    val collectionName = viewModel.savedStateHandle.get<String>("collectionName") ?: ""


    val title = when (collectionName) {
        "moviesLikes" -> buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.White)) {
                append("my ")
            }
            withStyle(style = SpanStyle(color = colorResource(R.color.lime))) {
                append("liked\n")
            }
            withStyle(style = SpanStyle(color = Color.White)) {
                append("movies")
            }
        }
        "moviesWatchlist" -> buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.White)) {
                append("my ")
            }
            withStyle(style = SpanStyle(color = colorResource(R.color.lime))) {
                append("watchlisted\n")
            }
            withStyle(style = SpanStyle(color = Color.White)) {
                append("movies")
            }
        }
        "moviesRated" -> buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.White)) {
                append("my ")
            }
            withStyle(style = SpanStyle(color = colorResource(R.color.lime))) {
                append("rated\n")
            }
            withStyle(style = SpanStyle(color = Color.White)) {
                append("movies")
            }
        }
        "TV seriesLikes" -> buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.White)) {
                append("my ")
            }
            withStyle(style = SpanStyle(color = colorResource(R.color.lime))) {
                append("liked\n")
            }
            withStyle(style = SpanStyle(color = Color.White)) {
                append("TV series")
            }
        }
        "TV seriesWatchlist" -> buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.White)) {
                append("my ")
            }
            withStyle(style = SpanStyle(color = colorResource(R.color.lime))) {
                append("watchlisted\n")
            }
            withStyle(style = SpanStyle(color = Color.White)) {
                append("TV series")
            }
        }
        "TV seriesRated" -> buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.White)) {
                append("my ")
            }
            withStyle(style = SpanStyle(color = colorResource(R.color.lime))) {
                append("rated\n")
            }
            withStyle(style = SpanStyle(color = Color.White)) {
                append("TV series")
            }
        }
        else -> buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.White)) {
                append(collectionName)
            }
        }
    }


    Box(modifier = Modifier.fillMaxSize().background(colorResource(R.color.dark_blue))) {
        Column {

            Spacer(modifier = Modifier.padding(top = 20.dp))
            TitleSectionCollection(
                title = title,
                onSortSelected = { sortType ->
                    viewModel.sortList(sortType)
                }
            )

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 85.dp),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(collection.value) { collectionItem ->
                    CollectionPosterItem(collectionItem) {
                        if (collectionName.contains("Movies", ignoreCase = true)) {
                            navController.navigate(AppScreens.MovieDetailScreen.route + "/${collectionItem.id}")
                        } else {
                            navController.navigate(AppScreens.TvShowDetailScreen.route + "/${collectionItem.id}")
                        }
                    }
                }

                if (isLoading.value) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
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
fun CollectionPosterItem(collectionItem: CollectionItemData, onClick: () -> Unit) {
    val painter = rememberAsyncImagePainter(
        model = "https://image.tmdb.org/t/p/w342${collectionItem.photoPath}"
    )
    val imageState = painter.state

    Box(
        modifier = Modifier
            .aspectRatio(0.67f)
            .clip(RectangleShape)
            .clickable { onClick() }
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        if (imageState is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator(modifier = Modifier.size(24.dp))
        }

        Image(
            painter = painter,
            contentDescription = collectionItem.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

