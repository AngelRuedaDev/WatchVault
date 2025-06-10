package com.angelruedadev.watchvault.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.angelruedadev.watchvault.data.local.entity.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies ORDER BY title ASC")
    suspend fun getAllMovies(): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieEntity: MovieEntity)

    @Query("SELECT * FROM movies WHERE id = :movieId LIMIT 1")
    suspend fun getMovieById(movieId: Int): MovieEntity?

    @Query("SELECT * FROM movies WHERE is_liked = 1")
    suspend fun getLikedMovies(): List<MovieEntity>

    @Query("SELECT * FROM movies WHERE is_inWatchList = 1")
    suspend fun getWatchListedMovies(): List<MovieEntity>

    @Query("SELECT * FROM movies WHERE user_rating is not 0")
    suspend fun getRatedMovies(): List<MovieEntity>

    @Delete
    suspend fun deleteMovie(movieEntity: MovieEntity)
}