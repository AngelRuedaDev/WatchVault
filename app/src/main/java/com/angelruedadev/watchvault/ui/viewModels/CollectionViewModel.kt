package com.angelruedadev.watchvault.ui.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelruedadev.watchvault.data.local.entity.CollectionItemData
import com.angelruedadev.watchvault.data.repository.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(private val localRepository: LocalRepository, val savedStateHandle: SavedStateHandle) : ViewModel(){
    private val _collectionItems = MutableStateFlow<List<CollectionItemData>>(emptyList())
    val collectionItems: StateFlow<List<CollectionItemData>> = _collectionItems

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private var originalCollectionItems: List<CollectionItemData> = emptyList()

    init {
        val collectionName = savedStateHandle.get<String>("collectionName") ?: ""

        when(collectionName){
            "moviesLikes" -> getLikedMovies()
            "moviesWatchlist" -> getWatchListedMovies()
            "moviesRated" -> getRatedMovies()
            "TV seriesLikes" -> getLikedTvShows()
            "TV seriesWatchlist" -> getWatchListedTvShows()
            "TV seriesRated" -> getRatedTvShows()
        }
    }


    private fun getLikedMovies() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = localRepository.getLikedMovies()
                originalCollectionItems = response
                _collectionItems.value = response

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
                originalCollectionItems = response
                _collectionItems.value = response

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
                originalCollectionItems = response
                _collectionItems.value = response

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
                originalCollectionItems = response
                _collectionItems.value = response

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
                originalCollectionItems = response
                _collectionItems.value = response

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
                originalCollectionItems = response
                _collectionItems.value = response

            }catch(e: Exception){
                _error.value = e.localizedMessage ?: "Unknown error"
            }finally {
                _isLoading.value = false
            }
        }
    }

    fun sortList(sortBy: String){
        when(sortBy){
            "titleAsc" -> {
                _collectionItems.value = _collectionItems.value.sortedBy { it.title }
            }
            "titleDesc" -> {
                _collectionItems.value = _collectionItems.value.sortedByDescending { it.title }
            }
            "ratingAsc" -> {
                _collectionItems.value = _collectionItems.value.sortedBy { it.userRating }
            }
            "ratingDesc" -> {
                _collectionItems.value = _collectionItems.value.sortedByDescending { it.userRating }
            }
            "default" -> _collectionItems.value = originalCollectionItems
        }
    }
}