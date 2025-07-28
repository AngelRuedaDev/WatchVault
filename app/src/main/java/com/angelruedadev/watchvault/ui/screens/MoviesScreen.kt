package com.angelruedadev.watchvault.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.angelruedadev.watchvault.ui.viewModels.MoviesViewModel
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.angelruedadev.watchvault.R
import com.angelruedadev.watchvault.domain.model.Movie
import com.angelruedadev.watchvault.ui.navigation.AppScreens
import com.angelruedadev.watchvault.ui.screens.components.GenreFilterDialog
import com.angelruedadev.watchvault.ui.screens.components.SearchSection
import com.angelruedadev.watchvault.ui.screens.components.TitleSection
import android.graphics.RenderEffect
import android.graphics.Shader
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.graphicsLayer
import android.os.Build
import androidx.compose.ui.graphics.asComposeRenderEffect

@Composable
fun MovieScreen(viewModel: MoviesViewModel = hiltViewModel(), navController: NavController) {
    val movies = viewModel.movies.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val error = viewModel.error.collectAsState()
    val genres = viewModel.genres.collectAsState()

    var selectedGenreIds by rememberSaveable { mutableStateOf(listOf<Int>()) }
    var query by rememberSaveable { mutableStateOf("") }
    var showGenresDialog by remember { mutableStateOf(false) }
    var isSearchClicked by remember { mutableStateOf(false) }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.dark_blue))
            .then(
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && showGenresDialog) {
                    Modifier.graphicsLayer {
                        renderEffect = RenderEffect
                            .createBlurEffect(10f, 10f, Shader.TileMode.CLAMP)
                            .asComposeRenderEffect()
                    }
                } else Modifier
            )
    ) {
        Column(modifier = Modifier.statusBarsPadding()) {

            if (isSearchClicked){
                SearchSection(
                    query = query,
                    onQueryChange = { query = it },
                    onSearch = { viewModel.onSearch(query) },
                    onFilterClick = { showGenresDialog = true },
                    isIdsEmpty = selectedGenreIds.isEmpty(),
                    onCloseSearch = { isSearchClicked = false }
                )
            }else{
                if (query.isNotEmpty() || selectedGenreIds.isNotEmpty()){
                    TitleSection("movies", onSearchClick = { isSearchClicked = true }, true)
                }else{
                    TitleSection("movies", onSearchClick = { isSearchClicked = true }, false)
                }

            }


            if (showGenresDialog) {
                GenreFilterDialog(
                    genres = genres.value,
                    initialSelectedIds = selectedGenreIds,
                    onDismiss = { showGenresDialog = false },
                    onApply = { selected ->
                        selectedGenreIds = selected
                        viewModel.onGenresSelected(selected)
                        showGenresDialog = false
                    },
                    onCloseSearch = { isSearchClicked = false }
                )
            }

            LazyColumn(
                contentPadding = PaddingValues(0.dp)
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

                item {
                    Spacer(modifier = Modifier.height(115.dp)) // Usa la altura de tu BottomNavigationBar
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
                            tint = colorResource(id = R.color.lime)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "There are no movies to show!",
                            color = Color.Red,
                            style = MaterialTheme.typography.bodyLarge
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

@SuppressLint("DefaultLocale")
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
        colors = CardColors(
            contentColor = Color.White,
            containerColor = colorResource(R.color.dark_blue),
            disabledContainerColor = Color.Red,
            disabledContentColor = Color.Red
        )
    ) {
        Row(modifier = Modifier.fillMaxSize().padding(8.dp)) {
            Box(
                modifier = Modifier
                    .width(90.dp)
                    .height(128.dp),
                contentAlignment = Alignment.Center
            ) {
                if (imageState is AsyncImagePainter.State.Loading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                }

                Image(
                    painter = painter,
                    contentDescription = movie.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(90.dp)
                        .height(128.dp)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f).padding(start = 20.dp)
            ) {
                Text(
                    text = movie.title,
                    color = colorResource(R.color.lime),
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = movie.releaseDate.take(4),
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter  = painterResource(id = R.drawable.ic_star_filled),
                        contentDescription = "Star Icon",
                        tint = colorResource(R.color.lime),
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = String.format("%.2f", movie.voteAverage),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }

    Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(colorResource(R.color.lime)))
}


