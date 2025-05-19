package com.angelruedadev.watchvault.data.repository

import com.angelruedadev.watchvault.BuildConfig
import com.angelruedadev.watchvault.data.apiService.TMDbApiService
import com.angelruedadev.watchvault.domain.model.TvResponse
import com.angelruedadev.watchvault.domain.model.TvShowDetail
import javax.inject.Inject

class TvShowRepository @Inject constructor(private val api: TMDbApiService) {
    suspend fun getAllTvShows(page: Int = 1): TvResponse {
        return api.discoverTvShows(BuildConfig.TMDB_API_KEY, page = page)
    }

    suspend fun searchTvShows(query: String, language: String = "en-US", page: Int = 1): TvResponse {
        return api.searchTvShows(BuildConfig.TMDB_API_KEY, query = query, language = language,  page = page)
    }

    suspend fun getTvShowDetails(tvShowId: Int, language: String = "en-US"): TvShowDetail {
        return api.getTvShowDetails(tvShowId, BuildConfig.TMDB_API_KEY, language)
    }

    suspend fun getTvShowsByGenres(genreIds: List<Int>, page: Int): TvResponse {
        val genreQuery = genreIds.joinToString(",") // Coma = AND
        return api.getTvShowsByGenre(BuildConfig.TMDB_API_KEY,genreQuery, page)
    }
}