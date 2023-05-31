package com.github.edwnmrtnz.showtime

import android.content.Context
import androidx.room.Room
import com.github.edwnmrtnz.showtime.core.MoviesRepository
import com.github.edwnmrtnz.showtime.core.data.DefaultMoviesRepository
import com.github.edwnmrtnz.showtime.core.data.api.MoviesAPI
import com.github.edwnmrtnz.showtime.core.data.db.MoviesDao
import com.github.edwnmrtnz.showtime.core.data.db.MoviesDatabase
import kotlinx.coroutines.Dispatchers
import okhttp3.HttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun provideMoviesDb(context: Context): MoviesDatabase {
    return Room.inMemoryDatabaseBuilder(context, MoviesDatabase::class.java)
        .build()
}

fun provideMoviesRepo(baseUrl: HttpUrl, moviesDao: MoviesDao): MoviesRepository {
    return DefaultMoviesRepository(
        Dispatchers.Unconfined,
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesAPI::class.java),
        moviesDao
    )
}
