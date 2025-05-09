package com.angelruedadev.watchvault.domain.model

import com.google.gson.annotations.SerializedName

data class MovieDetails(
    val id: Int,
    @SerializedName("backdrop_path") val backdropPath: String?,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String?,
    @SerializedName("origin_country") val originCountry: List<String>,
    @SerializedName("overview")val description: String?,
    val popularity: Double,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("production_companies") val productionCompanies: List<ProductionCompany>,
    @SerializedName("production_countries") val productionCountries: List<ProductionCountry>,
    @SerializedName("release_date") val releaseDate: String,
    val revenue: Long,
    @SerializedName("runtime") val duration: Int?,
    val status: String,
    @SerializedName("tagline") val subtitle: String?,
    val title: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int
)

data class Genre(
    val id: Int,
    val name: String
)

data class ProductionCompany(
    val id: Int,
    @SerializedName("logo_path") val logoPath: String?,
    val name: String,
    @SerializedName("origin_country") val originCountry: String
)

data class ProductionCountry(
    @SerializedName("iso_3166_1") val iso: String,
    val name: String
)