package com.angelruedadev.watchvault.data.apiService

import com.angelruedadev.watchvault.domain.model.MovieDetails
import com.angelruedadev.watchvault.domain.model.MovieResponse
import com.angelruedadev.watchvault.domain.model.TvResponse
import com.angelruedadev.watchvault.domain.model.TvShowDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDbApiService {
    /*
    * MOVIES
    * */
    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): MovieResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US"
    ): MovieDetails

    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("api_key") apiKey: String,
        @Query("with_genres") genreIds: String,
        @Query("page") page: Int
    ): MovieResponse

    /*
    * TV SHOWS
    * */
    @GET("discover/tv")
    suspend fun discoverTvShows(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): TvResponse

    @GET("search/tv")
    suspend fun searchTvShows(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): TvResponse

    @GET("tv/{tv_id}")
    suspend fun getTvShowDetails(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US"
    ): TvShowDetail

    @GET("discover/tv")
    suspend fun getTvShowsByGenre(
        @Query("api_key") apiKey: String,
        @Query("with_genres") genreIds: String,
        @Query("page") page: Int
    ): TvResponse
}