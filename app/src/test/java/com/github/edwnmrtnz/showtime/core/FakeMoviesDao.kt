package com.github.edwnmrtnz.showtime.core

import com.github.edwnmrtnz.showtime.core.data.db.CastDbRow
import com.github.edwnmrtnz.showtime.core.data.db.GenreDbRow
import com.github.edwnmrtnz.showtime.core.data.db.MovieDbRow
import com.github.edwnmrtnz.showtime.core.data.db.MovieWithGenreAndCastDbRow
import com.github.edwnmrtnz.showtime.core.data.db.MoviesDao
import com.github.edwnmrtnz.showtime.core.data.db.SectionedMovieDbRow

class FakeMoviesDao : MoviesDao {
    private val movies: HashMap<Int, MovieWithGenreAndCastDbRow> = hashMapOf()
    private val section: MutableSet<SectionedMovieDbRow> = mutableSetOf()

    override suspend fun get(id: Int): MovieWithGenreAndCastDbRow? {
        return movies[id]
    }

    override suspend fun get(section: String): List<SectionedMovieDbRow> {
        return this.section
            .filter { it.section.contentEquals(section) }
    }

    override suspend fun getSectionedMovies(): List<SectionedMovieDbRow> {
        return section.toList()
    }

    override suspend fun deleteSectionedMovies() {
        section.clear()
    }

    override suspend fun deleteMovies() {
        movies.clear()
    }

    override suspend fun saveMovie(movie: MovieDbRow) {
        error("favor saveMovie")
    }

    override suspend fun saveCasts(cast: List<CastDbRow>) {
        error("favor saveMovie")
    }

    override suspend fun saveGenres(genres: List<GenreDbRow>) {
        error("favor saveMovie")
    }

    override suspend fun saveMovie(movie: MovieWithGenreAndCastDbRow) {
        movies[movie.movie.id] = movie
    }

    override suspend fun saveSectionedMovie(section: SectionedMovieDbRow) {
        this.section.add(section)
    }
}
