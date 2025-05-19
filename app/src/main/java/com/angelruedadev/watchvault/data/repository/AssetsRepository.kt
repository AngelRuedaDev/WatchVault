package com.angelruedadev.watchvault.data.repository

import android.content.Context
import com.angelruedadev.watchvault.domain.model.Genre
import com.angelruedadev.watchvault.domain.model.GenreResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class AssetsRepository @Inject constructor(@ApplicationContext private val context: Context) {

    fun loadMovieGenres(): List<Genre> {
        val json = context.assets.open("genres_movies.json")
            .bufferedReader()
            .use { it.readText() }

        val gson = Gson()
        val type = object : TypeToken<GenreResponse>() {}.type
        return gson.fromJson<GenreResponse>(json, type).genres
    }

    fun loadTvShowGenres(): List<Genre> {
        val json = context.assets.open("genres_tv_show.json")
            .bufferedReader()
            .use { it.readText() }

        val gson = Gson()
        val type = object : TypeToken<GenreResponse>() {}.type
        return gson.fromJson<GenreResponse>(json, type).genres
    }
}