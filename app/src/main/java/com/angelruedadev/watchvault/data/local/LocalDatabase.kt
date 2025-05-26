package com.angelruedadev.watchvault.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.angelruedadev.watchvault.data.local.dao.MovieDao
import com.angelruedadev.watchvault.data.local.dao.TvShowDao
import com.angelruedadev.watchvault.data.local.entity.MovieEntity
import com.angelruedadev.watchvault.data.local.entity.TvShowEntity

@Database(
    entities = [MovieEntity::class, TvShowEntity::class],
    version = 1
)
abstract class LocalDatabase:RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
    abstract fun getTvShowDao(): TvShowDao
}