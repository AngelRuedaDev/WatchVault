package com.angelruedadev.watchvault.data.repository

import com.angelruedadev.watchvault.data.local.dao.MovieDao
import com.angelruedadev.watchvault.data.local.entity.MovieEntity
import javax.inject.Inject

class LocalRepository @Inject constructor(private val movieDao: MovieDao) {

    suspend fun getAllMovies(): List<MovieEntity>{
        return movieDao.getAllMovies()
    }

    suspend fun insertMovie(movieEntity: MovieEntity){
        movieDao.insertMovie(movieEntity)
    }

    suspend fun getMovieById(id: Int): MovieEntity?{
        return movieDao.getMovieById(id)
    }

    suspend fun deleteMovie(movieEntity: MovieEntity){
        movieDao.deleteMovie(movieEntity)
    }
}