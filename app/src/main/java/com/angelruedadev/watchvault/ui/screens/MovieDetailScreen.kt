@file:Suppress("UNUSED_EXPRESSION")

package com.angelruedadev.watchvault.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.angelruedadev.watchvault.data.local.entity.MovieEntity
import com.angelruedadev.watchvault.domain.model.MovieDetails
import com.angelruedadev.watchvault.ui.viewModels.MovieDetailViewModel

@Composable
fun MovieDetailScreen(id: Int, viewModel: MovieDetailViewModel = hiltViewModel()) {
    val detail = viewModel.movie.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val error = viewModel.error.collectAsState()

    val movieLocal = viewModel.movieEntity.collectAsState()

    LaunchedEffect(id) {
        viewModel.fetchDetail(id)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(WindowInsets.systemBars.only(WindowInsetsSides.Top).asPaddingValues())
    ){
        when{
            isLoading.value ->{
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            error.value != null ->{
                Text(
                    text = error.value ?: "Unknown error",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else -> {
                detail.value?.let { movie ->
                    Detail(movie, movieLocal.value, onToggleLike = { viewModel.toggleLike() }, onToggleWatchList = { viewModel.toggleWatchList() })


                } ?: run {
                    Text(
                        text = "No se pudieron cargar los detalles.",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}


@Composable
fun Detail(movie: MovieDetails, movieLocal: MovieEntity?, onToggleLike: () -> Unit, onToggleWatchList: () -> Unit) {
    Column {
        Text(movie.title)
        Text(movie.status)
        Text(movie.releaseDate)
        Text(movie.description.toString())
        Text(movie.budget.toString())
        Text(movie.duration.toString())

        Spacer(modifier = Modifier.padding(50.dp))

        IconButton(onClick = onToggleLike) {
            if (movieLocal == null || !movieLocal.isLiked){
                Icon(Icons.Default.FavoriteBorder, contentDescription = "Icon not on favourite")
            } else {
                Icon(Icons.Default.Favorite, contentDescription = "Icon favourite")
            }
        }

        IconButton(onClick = onToggleWatchList) {
            if (movieLocal == null || !movieLocal.isInWatchList){
                Icon(Icons.Default.Add, contentDescription = "Icon not on watchList")
            } else {
                Icon(Icons.Default.Check, contentDescription = "Icon InWatchList")
            }
        }

    }
}

