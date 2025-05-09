package com.angelruedadev.watchvault.data.repository

import com.angelruedadev.watchvault.BuildConfig
import com.angelruedadev.watchvault.data.apiService.TMDbApiService
import com.angelruedadev.watchvault.domain.model.MovieResponse
import javax.inject.Inject

class MovieRepository @Inject constructor(private val api: TMDbApiService) {

    suspend fun getAllMovies(page: Int = 1): MovieResponse {
        return api.discoverMovies(BuildConfig.TMDB_API_KEY, page = page)
    }
}