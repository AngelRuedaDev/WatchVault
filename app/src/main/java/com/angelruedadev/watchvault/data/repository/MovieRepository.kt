package com.angelruedadev.watchvault.data.repository

import com.angelruedadev.watchvault.BuildConfig
import com.angelruedadev.watchvault.data.apiService.TMDbApiService
import com.angelruedadev.watchvault.domain.model.MovieDetails
import com.angelruedadev.watchvault.domain.model.MovieResponse
import javax.inject.Inject

class MovieRepository @Inject constructor(private val api: TMDbApiService) {

    suspend fun getAllMovies(page: Int = 1): MovieResponse {
        return api.discoverMovies(BuildConfig.TMDB_API_KEY, page = page)
    }

    suspend fun searchMovies(query: String, language: String = "en-US", page: Int = 1): MovieResponse {
        return api.searchMovies(BuildConfig.TMDB_API_KEY, query = query, language = language,  page = page)
    }

    suspend fun getMovieDetails(movieId: Int, language: String = "en-US"): MovieDetails {
        return api.getMovieDetails(movieId, BuildConfig.TMDB_API_KEY, language)
    }
}