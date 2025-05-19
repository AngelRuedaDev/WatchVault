package com.angelruedadev.watchvault.domain.model

import com.google.gson.annotations.SerializedName

data class TvResponse(
    val page: Int,
    val results: List<TvShow>,
    @SerializedName("total_pages")val totalPages: Int,
    @SerializedName("total_results")val totalResults: Int
)