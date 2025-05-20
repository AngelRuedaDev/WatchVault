package com.angelruedadev.watchvault.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.angelruedadev.watchvault.data.local.dao.MovieDao
import com.angelruedadev.watchvault.data.local.entity.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1
)
abstract class LocalDatabase:RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
}