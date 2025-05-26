package com.angelruedadev.watchvault.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.angelruedadev.watchvault.data.local.entity.MovieEntity
import com.angelruedadev.watchvault.data.local.entity.TvShowEntity

@Dao
interface TvShowDao{

    @Query("SELECT * FROM tv_shows ORDER BY title ASC")
    suspend fun getAllTvShows(): List<TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShow(tvShowEntity: TvShowEntity)

    @Query("SELECT * FROM tv_shows WHERE id = :tvShowId LIMIT 1")
    suspend fun getTvShowById(tvShowId: Int): TvShowEntity?

    @Query("SELECT * FROM tv_shows WHERE is_liked = 1 ORDER BY title ASC")
    suspend fun getLikedTvShows(): List<TvShowEntity>

    @Query("SELECT * FROM tv_shows WHERE is_inWatchList = 1 ORDER BY title ASC")
    suspend fun getWatchListedTvShows(): List<TvShowEntity>

    @Query("SELECT * FROM tv_shows WHERE user_rating is not 0 ORDER BY title ASC")
    suspend fun getRatedTvShows(): List<TvShowEntity>

    @Delete
    suspend fun deleteTvShow(tvShowEntity: TvShowEntity)
}