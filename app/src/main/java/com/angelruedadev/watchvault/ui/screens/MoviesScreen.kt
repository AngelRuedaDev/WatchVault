package com.angelruedadev.watchvault.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.angelruedadev.watchvault.ui.viewModels.MoviesViewModel
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.angelruedadev.watchvault.domain.model.Genre
import com.angelruedadev.watchvault.domain.model.Movie
import com.angelruedadev.watchvault.ui.navigation.AppScreens
import com.angelruedadev.watchvault.ui.screens.components.GenreFilterDialog
import com.angelruedadev.watchvault.ui.screens.components.SearchSection

@Composable
fun MovieScreen(viewModel: MoviesViewModel = hiltViewModel(), navController: NavController) {
    val movies = viewModel.movies.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val error = viewModel.error.collectAsState()
    val genres = viewModel.genres.collectAsState()

    var selectedGenreIds by rememberSaveable { mutableStateOf(listOf<Int>()) }
    var query by rememberSaveable { mutableStateOf("") }
    var showGenresDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            SearchSection(
                query = query,
                onQueryChange = { query = it },
                onSearch = { viewModel.onSearch(query) },
                onFilterClick = { showGenresDialog = true },
                isIdsEmpty = selectedGenreIds.isEmpty()
            )

            if (showGenresDialog) {
                GenreFilterDialog(
                    genres = genres.value,
                    initialSelectedIds = selectedGenreIds,
                    onDismiss = { showGenresDialog = false },
                    onApply = { selected ->
                        selectedGenreIds = selected
                        viewModel.onGenresSelected(selected)
                        showGenresDialog = false
                    }
                )
            }

            LazyColumn(
                contentPadding = PaddingValues(16.dp)
            ) {
                itemsIndexed(movies.value) { index, movie ->
                    MovieItem(movie) {
                        navController.navigate(route = AppScreens.MovieDetailScreen.route + "/${movie.id}")
                    }

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

            if (movies.value.isEmpty() && !isLoading.value) {
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
fun MovieItem(movie: Movie, onClick: () -> Unit) {

    val painter = rememberAsyncImagePainter(
        model = "https://image.tmdb.org/t/p/w185${movie.posterPath}"
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


