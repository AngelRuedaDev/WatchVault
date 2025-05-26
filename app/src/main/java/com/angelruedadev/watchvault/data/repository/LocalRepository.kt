package com.angelruedadev.watchvault.data.repository

import com.angelruedadev.watchvault.data.local.dao.MovieDao
import com.angelruedadev.watchvault.data.local.dao.TvShowDao
import com.angelruedadev.watchvault.data.local.entity.MovieEntity
import com.angelruedadev.watchvault.data.local.entity.TvShowEntity
import javax.inject.Inject

class LocalRepository @Inject constructor(private val movieDao: MovieDao, private val tvShowDao: TvShowDao) {

    /*Movies*/

    suspend fun getAllMovies(): List<MovieEntity>{
        return movieDao.getAllMovies()
    }

    suspend fun insertMovie(movieEntity: MovieEntity){
        movieDao.insertMovie(movieEntity)
    }

    suspend fun getMovieById(id: Int): MovieEntity?{
        return movieDao.getMovieById(id)
    }

    suspend fun getLikedMovies():List<MovieEntity>{
        return movieDao.getLikedMovies()
    }

    suspend fun getWatchListedMovies():List<MovieEntity>{
        return movieDao.getWatchListedMovies()
    }

    suspend fun getRatedMovies():List<MovieEntity>{
        return movieDao.getRatedMovies()
    }

    suspend fun deleteMovie(movieEntity: MovieEntity){
        movieDao.deleteMovie(movieEntity)
    }

    /*TvShows*/

    suspend fun getAllTvShows(): List<TvShowEntity>{
        return tvShowDao.getAllTvShows()
    }

    suspend fun insertTvShow(tvShowEntity: TvShowEntity){
        tvShowDao.insertTvShow(tvShowEntity)
    }

    suspend fun getTvShowById(id: Int): TvShowEntity?{
        return tvShowDao.getTvShowById(id)
    }

    suspend fun getLikedTvShows():List<TvShowEntity>{
        return tvShowDao.getLikedTvShows()
    }

    suspend fun getWatchListedTvShows():List<TvShowEntity>{
        return tvShowDao.getWatchListedTvShows()
    }

    suspend fun getRatedTvShows():List<TvShowEntity>{
        return tvShowDao.getRatedTvShows()
    }

    suspend fun deleteTvShow(tvShowEntity: TvShowEntity){
        tvShowDao.deleteTvShow(tvShowEntity)
    }
}