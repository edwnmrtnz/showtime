package com.github.edwnmrtnz.showtime.core.data

import com.github.edwnmrtnz.showtime.core.Movie
import com.github.edwnmrtnz.showtime.core.MoviePreview
import com.github.edwnmrtnz.showtime.core.SectionedMovie
import com.github.edwnmrtnz.showtime.core.data.api.MovieResponse
import com.github.edwnmrtnz.showtime.core.data.api.MoviesResponse
import com.github.edwnmrtnz.showtime.core.data.db.CastDbRow
import com.github.edwnmrtnz.showtime.core.data.db.GenreDbRow
import com.github.edwnmrtnz.showtime.core.data.db.MovieDbRow
import com.github.edwnmrtnz.showtime.core.data.db.MovieWithGenreAndCastDbRow

private fun String.toPhoto(): String {
    return "${Constant.BASE_IMAGE_URL}$this"
}

fun MoviesResponse.toSectionedMovies(category: String): SectionedMovie {
    val movies = results
        .map { MoviePreview(id = it.id, thumbnail = it.posterPath.toPhoto()) }
        .toList()
    return SectionedMovie(category, movies)
}

fun MovieResponse.toMovie(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        thumbnail = this.posterPath.toPhoto(),
        overview = this.overview,
        keywords = this.genres.map { it.name },
        cast = this.casts.cast.map {
            Movie.Cast(
                "${Constant.BASE_IMAGE_URL}${it.profilePath}",
                it.name
            )
        }
    )
}

fun MovieResponse.toDb(): MovieWithGenreAndCastDbRow {
    val movie = MovieDbRow(
        id = id,
        adult = adult,
        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
    val genres = genres.map { GenreDbRow(id, it.name) }

    val casts = this.casts.cast.map { CastDbRow(id, it.profilePath?.toPhoto(), it.name) }
    return MovieWithGenreAndCastDbRow(movie, casts, genres)
}

fun MovieWithGenreAndCastDbRow.toMovie(): Movie {
    return Movie(
        id = movie.id,
        title = movie.title,
        thumbnail = movie.posterPath.toPhoto(),
        overview = movie.overview,
        keywords = genres.map { it.genre },
        cast = casts.map { Movie.Cast(it.photo?.toPhoto(), it.name) }
    )
}
