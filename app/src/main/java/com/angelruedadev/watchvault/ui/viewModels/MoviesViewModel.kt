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
    private var currentQuery = ""

    init {
        fetchMovies()
    }

    fun fetchMovies() {
        if (_isLoading.value || isLastPage) return

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {

                val response = if (currentQuery.isEmpty()){
                    Log.d("MOVIE", "Api call to fetch movies")
                    repository.getAllMovies(currentPage)
                }else{
                    Log.d("MOVIE", "Api call to search movies")
                    repository.searchMovies(query = currentQuery, page = currentPage)
                }

                if (response.results.isEmpty()){
                    isLastPage = true
                }else{
                    _movies.value = if (currentPage == 1) {
                        response.results
                    } else {
                        _movies.value + response.results
                    }
                    currentPage++
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onSearch(query: String) {
        val trimmedQuery = query.trim()
        if (trimmedQuery == currentQuery) return // Avoids repeating the query
        currentQuery = trimmedQuery
        resetPagination()
        fetchMovies()
    }

    private fun resetPagination() {
        currentPage = 1
        isLastPage = false
        _movies.value = emptyList()
    }
}