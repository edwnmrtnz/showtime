package com.github.edwnmrtnz.showtime.app.di

import com.github.edwnmrtnz.showtime.core.MoviesRepository
import com.github.edwnmrtnz.showtime.core.data.Constant
import com.github.edwnmrtnz.showtime.core.data.DefaultMoviesRepository
import com.github.edwnmrtnz.showtime.core.data.MoviesAPI
import com.github.edwnmrtnz.showtime.core.data.TMDBInterceptor
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

abstract class DataModule {
    @Binds
    @Singleton
    abstract fun movieRepository(repo: DefaultMoviesRepository): MoviesRepository

    companion object {
        @Provides
        fun provideMoviesAPI(): MoviesAPI {
            return Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    OkHttpClient()
                        .newBuilder()
                        .addInterceptor(TMDBInterceptor())
                        .build()
                )
                .build()
                .create(MoviesAPI::class.java)
        }

        @Provides
        @IODispatcher
        fun provideIODispatcher() = Dispatchers.IO
    }
}
