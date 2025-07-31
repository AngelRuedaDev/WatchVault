package com.angelruedadev.watchvault.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelruedadev.watchvault.data.local.entity.MovieEntity
import com.angelruedadev.watchvault.data.repository.LocalRepository
import com.angelruedadev.watchvault.data.repository.MovieRepository
import com.angelruedadev.watchvault.domain.model.MovieDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val movieRepository: MovieRepository, private val localRepository: LocalRepository) : ViewModel() {
    private val _movie = MutableStateFlow<MovieDetails?>(null)
    val movie: StateFlow<MovieDetails?> = _movie

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _movieEntity = MutableStateFlow<MovieEntity?>(null)
    val movieEntity : StateFlow<MovieEntity?> = _movieEntity


    fun fetchDetail(id: Int){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = movieRepository.getMovieDetails(id)
                _movie.value = response

                fetchInfoFromLocal(id)

            }catch(e: Exception){
                _error.value = e.localizedMessage ?: "Unknown error"
            }finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun fetchInfoFromLocal(id: Int) {
        _movieEntity.value = localRepository.getMovieById(id)
    }

    fun toggleLike() {
        viewModelScope.launch {
            val movie = _movie.value ?: return@launch

            var currentEntity = _movieEntity.value

            if (currentEntity == null) {
                currentEntity = MovieEntity(
                    id = movie.id,
                    title = movie.title,
                    photoPath = movie.posterPath,
                    isLiked = true
                )
                localRepository.insertMovie(currentEntity)
            } else {
                val updatedEntity = currentEntity.copy(isLiked = !currentEntity.isLiked)
                checkIfUpdateOrDelete(updatedEntity)
            }

            // Refresh the state from the local database
            fetchInfoFromLocal(movie.id)
        }
    }

    fun toggleWatchList() {
        viewModelScope.launch {
            val movie = _movie.value ?: return@launch

            var currentEntity = _movieEntity.value

            if (currentEntity == null) {
                currentEntity = MovieEntity(
                    id = movie.id,
                    title = movie.title,
                    photoPath = movie.posterPath,
                    isInWatchList = true
                )
                localRepository.insertMovie(currentEntity)
            } else {
                val updatedEntity = currentEntity.copy(isInWatchList = !currentEntity.isInWatchList)
                checkIfUpdateOrDelete(updatedEntity)
            }

            // Refresh the state from the local database
            fetchInfoFromLocal(movie.id)
        }
    }

    private suspend fun checkIfUpdateOrDelete(updatedEntity: MovieEntity) {
        val shouldDelete = !updatedEntity.isLiked && !updatedEntity.isInWatchList && updatedEntity.userRating == 0

        if (shouldDelete) {
            localRepository.deleteMovie(updatedEntity)
        } else {
            localRepository.insertMovie(updatedEntity)
        }
    }

    fun rateMovie(rating: Float) {
        viewModelScope.launch {
            val movie = _movie.value ?: return@launch

            var currentEntity = _movieEntity.value

            if (currentEntity == null) {
                currentEntity = MovieEntity(
                    id = movie.id,
                    title = movie.title,
                    photoPath = movie.posterPath,
                    userRating = (rating * 2).toInt()
                )
                localRepository.insertMovie(currentEntity)
            } else {
                val updatedEntity = currentEntity.copy(userRating = (rating * 2).toInt())
                checkIfUpdateOrDelete(updatedEntity)
            }

            // Refresh the state from the local database
            fetchInfoFromLocal(movie.id)
        }
    }
}