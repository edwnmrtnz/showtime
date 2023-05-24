package com.github.edwnmrtnz.showtime.app.di

import com.github.edwnmrtnz.showtime.core.MoviesRepository
import com.github.edwnmrtnz.showtime.core.data.DefaultMoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SingletonModule {

    @Binds
    @Singleton
    abstract fun movieRepository(repo: DefaultMoviesRepository): MoviesRepository
}
