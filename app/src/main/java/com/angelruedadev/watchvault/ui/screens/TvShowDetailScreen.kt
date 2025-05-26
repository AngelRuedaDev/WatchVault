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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.angelruedadev.watchvault.data.local.entity.MovieEntity
import com.angelruedadev.watchvault.data.local.entity.TvShowEntity
import com.angelruedadev.watchvault.domain.model.MovieDetails
import com.angelruedadev.watchvault.domain.model.TvShowDetail
import com.angelruedadev.watchvault.ui.screens.components.RateDialog
import com.angelruedadev.watchvault.ui.viewModels.TvShowDetailViewModel

@Composable
fun TvShowDetailScreen(id: Int, viewModel: TvShowDetailViewModel = hiltViewModel()) {
    val detail = viewModel.tvShow.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val error = viewModel.error.collectAsState()

    val tvShowLocal = viewModel.tvShowEntity.collectAsState()
    var showRateDialog by remember { mutableStateOf(false) }

    LaunchedEffect(id) {
        viewModel.fetchDetail(id)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(WindowInsets.systemBars.only(WindowInsetsSides.Top).asPaddingValues())
    ){
        if (showRateDialog) {
            RateDialog(
                currentRating = (tvShowLocal.value?.userRating ?: 0) / 2f,
                onDismiss = { showRateDialog = false },
                onConfirm = { newRating ->
                    viewModel.rateTvShow(newRating)
                    showRateDialog = false
                }
            )
        }

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
                detail.value?.let { tvShow ->
                    DetailTvShow(
                        tvShow,
                        tvShowLocal.value,
                        onToggleLike = { viewModel.toggleLike() },
                        onToggleWatchList = { viewModel.toggleWatchList() },
                        openRateDialog = { showRateDialog = true }
                    )
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
fun DetailTvShow(
    tvShow: TvShowDetail,
    tvShowLocal: TvShowEntity?,
    onToggleLike: () -> Unit,
    onToggleWatchList: () -> Unit,
    openRateDialog: () -> Unit
) {
    Column {
        Text(tvShow.name)
        Text(tvShow.status?: "unknown")
        Text(tvShow.firstAirDate?: "unknown")
        Text(tvShow.description.toString())

        Spacer(modifier = Modifier.padding(50.dp))

        IconButton(onClick = onToggleLike) {
            if (tvShowLocal == null || !tvShowLocal.isLiked) {
                Icon(Icons.Default.FavoriteBorder, contentDescription = "Icon not on favourite")
            } else {
                Icon(Icons.Default.Favorite, contentDescription = "Icon favourite")
            }
        }

        IconButton(onClick = onToggleWatchList) {
            if (tvShowLocal == null || !tvShowLocal.isInWatchList) {
                Icon(Icons.Default.Add, contentDescription = "Icon not on watchList")
            } else {
                Icon(Icons.Default.Check, contentDescription = "Icon InWatchList")
            }
        }

        Spacer(modifier = Modifier.padding(50.dp))
        Text("rating:")

        IconButton(onClick = openRateDialog) {
            Icon(
                Icons.Default.Menu,
                contentDescription = "Filter Icon"
            )
        }
    }
}