package com.angelruedadev.watchvault.di

import android.content.Context
import androidx.room.Room
import com.angelruedadev.watchvault.data.local.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val LOCAL_DATABASE = "local_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, LocalDatabase::class.java, LOCAL_DATABASE).build()

    @Singleton
    @Provides
    fun provideMovieDao(db: LocalDatabase)= db.getMovieDao()

    @Singleton
    @Provides
    fun provideTvShowDao(db: LocalDatabase)= db.getTvShowDao()
}