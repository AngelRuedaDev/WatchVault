package com.angelruedadev.watchvault.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.angelruedadev.watchvault.domain.model.MovieDetails
import com.angelruedadev.watchvault.ui.viewModels.MovieDetailViewModel

@Composable
fun MovieDetailScreen(id: Int, viewModel: MovieDetailViewModel = hiltViewModel()) {
    val detail = viewModel.movie.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val error = viewModel.error.collectAsState()

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
                    Detail(movie)
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
fun Detail(movie: MovieDetails) {
    Column {
        Text(movie.title)
        Text(movie.status)
        Text(movie.releaseDate)
        Text(movie.description.toString())
        Text(movie.budget.toString())
        Text(movie.duration.toString())
    }
}

