package com.angelruedadev.watchvault.ui.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelruedadev.watchvault.data.repository.MovieRepository
import com.angelruedadev.watchvault.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private var currentPage = 1
    private var isLastPage = false

    init {
        fetchMovies()
    }

    fun fetchMovies() {
        if (_isLoading.value || isLastPage) return

        viewModelScope.launch {
            _isLoading.value = true

            try {
                Log.d("MOVIE", "Api call to fetch movies")
                val newMovies = repository.getAllMovies(currentPage)
                if (newMovies.results.isEmpty()) {
                    isLastPage = true
                } else {
                    val updatedList = _movies.value + newMovies.results
                    _movies.value = updatedList
                    currentPage++ // advance to next page
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}