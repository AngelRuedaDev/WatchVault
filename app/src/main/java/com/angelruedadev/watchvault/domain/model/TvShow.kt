package com.angelruedadev.watchvault.domain.model

import com.google.gson.annotations.SerializedName

data class TvShow(
    val id: Int,
    val name: String,
    val overview: String,
    @SerializedName("poster_path")val posterPath: String?,
    @SerializedName("backdrop_path")val backdropPath: String?,
    @SerializedName("vote_average")val voteAverage: Double,
    @SerializedName("vote_count")val voteCount: Int,
    @SerializedName("genre_ids")val genreIds: List<Int>,
    @SerializedName("original_language")val originalLanguage: String,
    @SerializedName("original_name")val originalTitle: String,
    @SerializedName("first_air_date")val firstAirDate: String,
    val popularity: Double,
)