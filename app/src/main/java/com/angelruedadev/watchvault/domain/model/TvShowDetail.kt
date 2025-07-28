package com.angelruedadev.watchvault.domain.model

import com.google.gson.annotations.SerializedName

data class TvShowDetail(
    val id: Int,
    val name: String,
    @SerializedName("tagline") val subtitle: String?,
    @SerializedName("overview") val description: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("created_by") val createdBy: List<Created>,
    @SerializedName("first_air_date") val firstAirDate: String?,
    @SerializedName("last_air_date") val lastAirDate: String?,
    val genres: List<Genre>,
    val homepage: String?,
    @SerializedName("number_of_episodes") val numberOfEpisodes: Int,
    @SerializedName("number_of_seasons") val numberOfSeasons: Int,
    @SerializedName("original_language") val originalLanguage: String?,
    val popularity: Double,
    @SerializedName("production_companies") val productionCompanies: List<ProductionCompanyTvShow>?,
    val seasons: List<Season>?,
    val status: String?,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int

)

data class Created(
    val id: Int,
    val name: String,
    @SerializedName("profile_path") val profilePath: String?,
)

data class Season(
    @SerializedName("air_date") val airDate: String?,
    @SerializedName("episode_count") val numberOfEpisodes: Int,
    val name: String,
    @SerializedName("overview") val description: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("vote_average") val voteAverage: Double?,
)

data class ProductionCompanyTvShow(
    val id: Int,
    @SerializedName("logo_path") val logoPath: String?,
    val name: String,
    @SerializedName("origin_country") val originCountry: String
)
