package com.github.edwnmrtnz.showtime.core.data

import com.github.edwnmrtnz.showtime.core.Movie
import com.github.edwnmrtnz.showtime.core.MoviePreview
import com.github.edwnmrtnz.showtime.core.SectionedMovie

fun MoviesResponse.toSectionedMovies(category: String): SectionedMovie {
    val movies = results
        .map { MoviePreview(id = it.id, thumbnail = "${Constant.BASE_IMAGE_URL}${it.posterPath}") }
        .toList()
    return SectionedMovie(category, movies)
}

fun MovieResponse.toMovie(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        thumbnail = "${Constant.BASE_IMAGE_URL}${this.posterPath}",
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
