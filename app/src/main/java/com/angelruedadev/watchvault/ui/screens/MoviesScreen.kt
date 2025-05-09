package com.angelruedadev.watchvault.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.angelruedadev.watchvault.ui.viewModels.MoviesViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.angelruedadev.watchvault.domain.model.Movie

@Composable
fun MovieScreen(viewModel: MoviesViewModel = hiltViewModel()) {
    val movies = viewModel.movies.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val error = viewModel.error.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp)
        ) {
            itemsIndexed(movies.value) { index, movie ->
                MovieItem(movie)

                // Scroll infinito cuando queden 5 para el final
                if (index >= movies.value.lastIndex - 5 && !isLoading.value) {
                    viewModel.fetchMovies()
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

        // Show error
        if (error.toString().isNotEmpty() && movies.value.isEmpty()) {
            Text(
                text = "Error: $error",
                color = Color.Red,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun MovieItem(movie: Movie) {

    val painter = rememberAsyncImagePainter(
        model = "https://image.tmdb.org/t/p/w185${movie.posterPath}"
    )
    val imageState = painter.state

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
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
                    contentDescription = movie.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

