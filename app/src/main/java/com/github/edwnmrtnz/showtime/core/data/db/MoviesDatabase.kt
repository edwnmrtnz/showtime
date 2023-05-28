package com.github.edwnmrtnz.showtime.core.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [MovieDbRow::class, GenreDbRow::class, CastDbRow::class, SectionedMovieDbRow::class],
    version = 2,
    exportSchema = false
)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun dao(): MoviesDao

    companion object {
        @Volatile
        private var INSTANCE: MoviesDatabase? = null
        fun getInstance(context: Context): MoviesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MoviesDatabase::class.java,
                    "showtime.db"
                ).fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
