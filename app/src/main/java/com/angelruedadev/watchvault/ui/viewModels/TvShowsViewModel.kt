package com.angelruedadev.watchvault.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelruedadev.watchvault.data.repository.AssetsRepository
import com.angelruedadev.watchvault.data.repository.TvShowRepository
import com.angelruedadev.watchvault.domain.model.Genre
import com.angelruedadev.watchvault.domain.model.TvShow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowsViewModel @Inject constructor (private val tvShowRepository: TvShowRepository, private val assetsRepository: AssetsRepository) : ViewModel(){

    private val _tvShows = MutableStateFlow<List<TvShow>>(emptyList())
    val tvShow: StateFlow<List<TvShow>> = _tvShows

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
        fetchTvShows()
        fetchGenres()
    }

    fun fetchTvShows(){
        if (_isLoading.value || isLastPage) return

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try{
                val response = when{
                    currentQuery.isNotEmpty() && _selectedGenres.value.isNotEmpty() -> {
                        val searchResponse = tvShowRepository.searchTvShows(query = currentQuery, page = currentPage)
                        searchResponse.copy(results = filterResults(searchResponse.results))
                    }

                    currentQuery.isNotEmpty() -> {
                        tvShowRepository.searchTvShows(query = currentQuery, page = currentPage)
                    }

                    _selectedGenres.value.isNotEmpty() -> {
                        tvShowRepository.getTvShowsByGenres(_selectedGenres.value, currentPage)
                    }

                    else -> {
                        tvShowRepository.getAllTvShows(currentPage)
                    }
                }

                if (response.results.isEmpty()){
                    isLastPage = true
                }else{
                    _tvShows.value = if (currentPage == 1){
                        response.results
                    }else{
                        _tvShows.value + response.results
                    }
                    currentPage++
                }

            }catch (e: Exception){
                _error.value = e.message
            }finally {
                _isLoading.value = false
            }
        }
    }

    private fun filterResults(results: List<TvShow>): List<TvShow>{
        return  results.filter { tvShow ->
            val genres = tvShow.genreIds ?: emptyList()
            _selectedGenres.value.all { genreId -> genres.contains(genreId) }
        }
    }

    private fun fetchGenres() {
        _genres.value = assetsRepository.loadTvShowGenres()
    }

    fun onSearch(query: String) {
        val trimmedQuery = query.trim()
        if (trimmedQuery == currentQuery) return // Avoids repeating the query
        currentQuery = trimmedQuery
        resetPagination()
        fetchTvShows()
    }

    fun onGenresSelected(genreIds: List<Int>) {
        _selectedGenres.value = genreIds
        resetPagination()
        fetchTvShows()
    }

    private fun resetPagination() {
        currentPage = 1
        isLastPage = false
        _tvShows.value = emptyList()
    }
}