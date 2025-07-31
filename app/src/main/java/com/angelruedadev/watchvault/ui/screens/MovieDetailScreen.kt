package com.angelruedadev.watchvault.ui.screens

import android.annotation.SuppressLint
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.angelruedadev.watchvault.R
import com.angelruedadev.watchvault.data.local.entity.MovieEntity
import com.angelruedadev.watchvault.domain.model.MovieDetails
import com.angelruedadev.watchvault.ui.screens.components.ActionFabRow
import com.angelruedadev.watchvault.ui.screens.components.GenreItem
import com.angelruedadev.watchvault.ui.screens.components.RateDialog
import com.angelruedadev.watchvault.ui.screens.components.RatingStars
import com.angelruedadev.watchvault.ui.viewModels.MovieDetailViewModel

@Composable
fun MovieDetailScreen(id: Int, viewModel: MovieDetailViewModel = hiltViewModel()) {
    val detail = viewModel.movie.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val error = viewModel.error.collectAsState()

    val movieLocal = viewModel.movieEntity.collectAsState()
    var showRateDialog by remember { mutableStateOf(false) }
    var expandedFab by remember { mutableStateOf(false) }

    LaunchedEffect(id) {
        viewModel.fetchDetail(id)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.dark_blue))
            .padding(WindowInsets.systemBars.only(WindowInsetsSides.Top).asPaddingValues())
            .then(
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && showRateDialog) {
                    Modifier.graphicsLayer {
                        renderEffect = RenderEffect
                            .createBlurEffect(8f, 8f, Shader.TileMode.CLAMP)
                            .asComposeRenderEffect()
                    }
                } else Modifier
            )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            when {
                isLoading.value -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                error.value != null -> {
                    Text(
                        text = error.value ?: "Unknown error",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    detail.value?.let { movie ->
                        DetailMovie(
                            movie,
                            movieLocal.value
                        )
                    } ?: run {
                        Text(
                            text = "There was an error loading the information.",
                            color = Color.Red,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }

            if (showRateDialog) {
                RateDialog(
                    currentRating = (movieLocal.value?.userRating ?: 0) / 2f,
                    onDismiss = { showRateDialog = false },
                    onConfirm = { newRating ->
                        viewModel.rateMovie(newRating)
                        showRateDialog = false
                    }
                )
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && expandedFab) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xBB000000))
                        .clickable { expandedFab = false }
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 50.dp, end = 25.dp),
                contentAlignment = Alignment.BottomEnd
            ) {


                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    AnimatedVisibility(visible = expandedFab) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(2.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            var colorLiked = colorResource(R.color.white)
                            var colorWatchList = colorResource(R.color.white)
                            var colorRate = colorResource(R.color.white)

                            var textLiked = "like it"
                            var textWatchList = "watchlist it"
                            var textRate = "rate it"

                            if (movieLocal.value != null && movieLocal.value!!.isLiked){
                                colorLiked = colorResource(R.color.lime)
                                textLiked = "liked"
                            }
                            if (movieLocal.value != null && movieLocal.value!!.isInWatchList){
                                colorWatchList = colorResource(R.color.lime)
                                textWatchList = "watchlisted"
                            }
                            if (movieLocal.value != null && movieLocal.value!!.userRating != 0){
                                colorRate = colorResource(R.color.lime)
                                textRate = "rated"
                            }

                            ActionFabRow(
                                text = textLiked,
                                painter = painterResource(id = R.drawable.ic_like),
                                onClick = {
                                    viewModel.toggleLike()
                                    expandedFab = false
                                },
                                color = colorLiked
                            )

                            ActionFabRow(
                                text = textWatchList,
                                painter = painterResource(id = R.drawable.ic_watchlist),
                                onClick = {
                                    viewModel.toggleWatchList()
                                    expandedFab = false
                                },
                                color = colorWatchList,
                            )

                            ActionFabRow(
                                text = textRate,
                                painter = painterResource(id = R.drawable.ic_star),
                                onClick = {
                                    showRateDialog = true
                                    expandedFab = false
                                },
                                color = colorRate,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    FloatingActionButton(
                        onClick = { expandedFab = !expandedFab },
                        containerColor = colorResource(R.color.lime),
                        shape = CircleShape
                    ) {
                        Icon(
                            painter = if (expandedFab) painterResource(id = R.drawable.ic_pencil) else painterResource(id = R.drawable.ic_add),
                            contentDescription = "Toggle Actions",
                            tint = colorResource(R.color.dark_blue)
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun DetailMovie(
    movie: MovieDetails,
    movieLocal: MovieEntity?
) {
    LazyColumn {
        item {
            MovieBanner(movie, movieLocal)
        }
        item {
            MovieInfo(movie)
        }

        if (movie.productionCompanies.isNotEmpty()){
            item {
                MovieProduction(movie)
            }
        }

        item {
            MovieOtherDetails(movie)
        }
    }
}

@Composable
fun MovieOtherDetails(movie: MovieDetails) {
    Column(modifier = Modifier.padding(top = 22.dp, bottom = 25.dp)) {
        Text(
            text = "details",
            style = MaterialTheme.typography.bodyLarge,
            color = colorResource(R.color.lime),
            fontSize = 24.sp,
            modifier = Modifier.padding(start = 13.dp, end = 13.dp, bottom = 8.dp)
        )

        if(movie.budget != 0){
            Spacer(modifier = Modifier.padding(top = 10.dp))

            Text(
                text = "budget",
                style = MaterialTheme.typography.bodySmall,
                color = colorResource(R.color.white),
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 13.dp, end = 13.dp, bottom = 3.dp)
            )

            Row(modifier = Modifier.padding(start = 15.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(painterResource(id = R.drawable.ic_dollar), contentDescription = "dollar icon", tint = colorResource(R.color.lime))

                Text(
                    text = formatBudget(movie.budget.toLong()),
                    style = MaterialTheme.typography.bodySmall,
                    color = colorResource(R.color.white),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        if(movie.revenue.toInt() != 0){
            Spacer(modifier = Modifier.padding(top = 10.dp))

            Text(
                text = "revenue",
                style = MaterialTheme.typography.bodySmall,
                color = colorResource(R.color.white),
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 13.dp, end = 13.dp, bottom = 3.dp)
            )

            Row(modifier = Modifier.padding(start = 15.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(painterResource(id = R.drawable.ic_dollar), contentDescription = "dollar icon", tint = colorResource(R.color.lime))

                Text(
                    text = formatBudget(movie.revenue),
                    style = MaterialTheme.typography.bodySmall,
                    color = colorResource(R.color.white),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        if (movie.productionCountries.isNotEmpty()){
            Spacer(modifier = Modifier.padding(top = 10.dp))

            Text(
                text = "production countries",
                style = MaterialTheme.typography.bodySmall,
                color = colorResource(R.color.white),
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 13.dp, end = 13.dp, bottom = 3.dp)
            )

            movie.productionCountries.forEach { country ->

                Row(modifier = Modifier.padding(start = 15.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(painterResource(id = R.drawable.ic_locaiton), contentDescription = "location icon", tint = colorResource(R.color.lime))

                    Text(
                        text = country.name,
                        style = MaterialTheme.typography.bodySmall,
                        color = colorResource(R.color.white),
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}

private fun formatBudget(value: Long): String {
    return when {
        value >= 1_000_000_000 -> "${(value / 1_000_000_000.0).format(1)} B"
        value >= 1_000_000     -> "${(value / 1_000_000.0).format(1)} M"
        value >= 1_000         -> "${(value / 1_000.0).format(1)} K"
        else                   -> "$value"
    }
}

private fun Double.format(digits: Int) = "%.${digits}f".format(this)

@Composable
fun MovieProduction(movie: MovieDetails) {
    Column(modifier = Modifier.padding(top = 22.dp)) {
        Text(
            text = "production companies",
            style = MaterialTheme.typography.bodyLarge,
            color = colorResource(R.color.lime),
            fontSize = 24.sp,
            modifier = Modifier.padding(start = 13.dp, end = 13.dp, bottom = 8.dp)
        )

        LazyRow(modifier = Modifier.padding(top = 10.dp, end = 13.dp, start = 13.dp)) {

            Log.d("production", movie.productionCompanies.toString())

            movie.productionCompanies.forEach { productionCompanyTvShow ->
                item {
                    Column(
                        modifier = Modifier.width(115.dp).padding(horizontal = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/w200${productionCompanyTvShow.logoPath}",
                            contentDescription = productionCompanyTvShow.name,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .width(75.dp)
                                .height(75.dp)
                                .background(colorResource(R.color.white))
                                .padding(5.dp)
                        )

                        Text(
                            text = productionCompanyTvShow.name,
                            style = MaterialTheme.typography.bodySmall.copy(lineHeight = 15.sp),
                            color = colorResource(R.color.white),
                            fontSize = 13.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 6.dp)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MovieInfo(movie: MovieDetails) {
    if (!movie.subtitle.isNullOrEmpty()) {
        Text(
            text = movie.subtitle,
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 15.sp,
            modifier = Modifier.padding(bottom = 15.dp, start = 13.dp, end = 13.dp),
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.lime)

        )
    }

    Text(
        text = movie.description!!,
        style = MaterialTheme.typography.bodySmall,
        fontSize = 15.sp,
        modifier = Modifier.padding(bottom = 13.dp, start = 13.dp, end = 13.dp),
        fontWeight = FontWeight.Normal,
        color = colorResource(R.color.white)
    )

    FlowRow(
        modifier = Modifier.padding(horizontal = 13.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        movie.genres.forEach { genre ->
            GenreItem(genre.name)
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun MovieBanner(movie: MovieDetails, movieLocal: MovieEntity?) {
    val painterBanner = rememberAsyncImagePainter(
        model = "https://image.tmdb.org/t/p/w500${movie.backdropPath}"
    )
    val imageStateBanner = painterBanner.state

    val painterPoster = rememberAsyncImagePainter(
        model = "https://image.tmdb.org/t/p/w200${movie.posterPath}"
    )

    val gradientBrush = Brush.verticalGradient(
        colorStops = arrayOf(
            0.0f to colorResource(R.color.dark_blue).copy(alpha = 0f),
            0.5f to colorResource(R.color.dark_blue).copy(alpha = 0.75f),
            1.0f to colorResource(R.color.dark_blue).copy(alpha = 1f)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 25.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(171.dp)
                    .align(Alignment.BottomStart)
                    .padding(horizontal = 13.dp)
                    .zIndex(10f)
            ) {
                Image(
                    painter = painterPoster,
                    contentDescription = movie.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(120.dp)
                        .height(171.dp)
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 15.dp)
                ) {

                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 20.sp,
                        color = colorResource(R.color.white),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    Text(
                        text = movie.status,
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 14.sp,
                        color = colorResource(R.color.white),
                        modifier = Modifier.padding(vertical = 3.dp)
                    )

                    Text(
                        text = movie.releaseDate,
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 14.sp,
                        color = colorResource(R.color.white),
                        modifier = Modifier.padding(vertical = 3.dp)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 3.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_star_filled),
                            contentDescription = "Star Icon",
                            tint = colorResource(R.color.lime),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = String.format("%.2f", movie.voteAverage),
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 14.sp,
                            color = colorResource(R.color.white)
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.BottomCenter)
                    .background(brush = gradientBrush)
                    .zIndex(6f)
            )

            if (imageStateBanner is AsyncImagePainter.State.Loading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            }

            Image(
                painter = painterBanner,
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(5f)
            )
        }



        if (movieLocal != null && movieLocal.userRating != 0) {

            Spacer(modifier = Modifier.padding(vertical = 15.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(R.color.lime))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "your rating is",
                    color = colorResource(R.color.dark_blue),
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.width(8.dp))
                RatingStars(rating = (movieLocal.userRating) / 2f)
            }
        }
    }
}





