package com.github.edwnmrtnz.showtime.core.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface MoviesDao {

    @Transaction
    @Query("SELECT * FROM movies WHERE id=:id")
    suspend fun get(id: Int): MovieWithGenreAndCastDbRow?

    @Query("SELECT * FROM sectioned_movies WHERE section =:section")
    suspend fun get(section: String): List<SectionedMovieDbRow>

    @Query("SELECT * FROM sectioned_movies")
    suspend fun getSectionedMovies(): List<SectionedMovieDbRow>

    @Query("DELETE FROM sectioned_movies")
    suspend fun deleteSectionedMovies()

    @Query("DELETE FROM movies")
    suspend fun deleteMovies()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovie(movie: MovieDbRow)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCasts(cast: List<CastDbRow>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveGenres(genres: List<GenreDbRow>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovie(section: SectionedMovieDbRow)

    @Transaction
    suspend fun saveMovie(movie: MovieWithGenreAndCastDbRow) {
        saveMovie(movie.movie)
        saveGenres(movie.genres)
        saveCasts(movie.casts)
    }
}
