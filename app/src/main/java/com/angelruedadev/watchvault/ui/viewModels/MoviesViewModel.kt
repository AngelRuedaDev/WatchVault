package com.angelruedadev.watchvault.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelruedadev.watchvault.data.repository.AssetsRepository
import com.angelruedadev.watchvault.data.repository.MovieRepository
import com.angelruedadev.watchvault.domain.model.Genre
import com.angelruedadev.watchvault.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val movieRepository: MovieRepository, private val assetsRepository: AssetsRepository ) : ViewModel() {

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _genres = MutableStateFlow<List<Genre>>(emptyList())
    val genres: StateFlow<List<Genre>> = _genres

    private val _selectedGenres = MutableStateFlow<List<Int>>(emptyList())
    val selectedGenres: StateFlow<List<Int>> = _selectedGenres

    private var currentPage = 1
    private var isLastPage = false
    private var currentQuery = ""

    init {
        fetchMovies()
        fetchGenres()
    }

    fun fetchMovies() {
        if (_isLoading.value || isLastPage) return

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {

                val response = when {
                    currentQuery.isNotEmpty() && _selectedGenres.value.isNotEmpty() -> {
                        Log.d("Movie", "${_selectedGenres.value} $currentQuery")

                        val searchResponse = movieRepository.searchMovies(query = currentQuery, page = currentPage)
                        searchResponse.copy(results = filterResults(searchResponse.results))
                    }

                    currentQuery.isNotEmpty() -> {
                        movieRepository.searchMovies(query = currentQuery, page = currentPage)
                    }
                    _selectedGenres.value.isNotEmpty() -> {

                        movieRepository.getMoviesByGenres(_selectedGenres.value, currentPage)
                    }
                    else -> {
                        movieRepository.getAllMovies(currentPage)
                    }
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

    private fun filterResults(results: List<Movie>): List<Movie> {
        return results.filter { movie ->
            val genres = movie.genreIds
            _selectedGenres.value.all { genreId -> genres.contains(genreId) }
        }
    }

    private fun fetchGenres() {
        _genres.value = assetsRepository.loadMovieGenres()
    }

    fun onSearch(query: String) {
        val trimmedQuery = query.trim()
        if (trimmedQuery == currentQuery) return // Avoids repeating the query
        currentQuery = trimmedQuery
        resetPagination()
        fetchMovies()
    }

    fun onGenresSelected(genreIds: List<Int>) {
        _selectedGenres.value = genreIds
        resetPagination()
        fetchMovies()
    }

    private fun resetPagination() {
        currentPage = 1
        isLastPage = false
        _movies.value = emptyList()
    }
}