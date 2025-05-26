package com.angelruedadev.watchvault.ui.viewModels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelruedadev.watchvault.data.local.entity.CollectionItemData
import com.angelruedadev.watchvault.data.local.entity.MovieEntity
import com.angelruedadev.watchvault.data.repository.LocalRepository
import com.angelruedadev.watchvault.data.repository.MovieRepository
import com.angelruedadev.watchvault.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(private val localRepository: LocalRepository, val savedStateHandle: SavedStateHandle) : ViewModel(){
    private val _collectionItem = MutableStateFlow<List<CollectionItemData>>(emptyList())
    val collectionItem: StateFlow<List<CollectionItemData>> = _collectionItem

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        val collectionName = savedStateHandle.get<String>("collectionName") ?: ""

        when(collectionName){
            "MoviesLikes" -> getLikedMovies()
            "MoviesWatchlist" -> getWatchListedMovies()
            "MoviesRated" -> getRatedMovies()
            "TvShowsLikes" -> getLikedTvShows()
            "TvShowsWatchlist" -> getWatchListedTvShows()
            "TvShowsRated" -> getRatedTvShows()
        }
    }


    private fun getLikedMovies() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = localRepository.getLikedMovies()
                _collectionItem.value = response

            }catch(e: Exception){
                _error.value = e.localizedMessage ?: "Unknown error"
            }finally {
                _isLoading.value = false
            }
        }
    }

    private fun getWatchListedMovies() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = localRepository.getWatchListedMovies()
                _collectionItem.value = response

            }catch(e: Exception){
                _error.value = e.localizedMessage ?: "Unknown error"
            }finally {
                _isLoading.value = false
            }
        }
    }

    private fun getRatedMovies() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = localRepository.getRatedMovies()
                _collectionItem.value = response

            }catch(e: Exception){
                _error.value = e.localizedMessage ?: "Unknown error"
            }finally {
                _isLoading.value = false
            }
        }
    }

    private fun getLikedTvShows() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = localRepository.getLikedTvShows()
                _collectionItem.value = response

            }catch(e: Exception){
                _error.value = e.localizedMessage ?: "Unknown error"
            }finally {
                _isLoading.value = false
            }
        }
    }

    private fun getWatchListedTvShows() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = localRepository.getWatchListedTvShows()
                _collectionItem.value = response

            }catch(e: Exception){
                _error.value = e.localizedMessage ?: "Unknown error"
            }finally {
                _isLoading.value = false
            }
        }
    }

    private fun getRatedTvShows() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = localRepository.getRatedTvShows()
                _collectionItem.value = response

            }catch(e: Exception){
                _error.value = e.localizedMessage ?: "Unknown error"
            }finally {
                _isLoading.value = false
            }
        }
    }
}