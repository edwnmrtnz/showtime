package com.github.edwnmrtnz.showtime.ui.home

import com.github.edwnmrtnz.showtime.core.MoviePreview
import com.github.edwnmrtnz.showtime.core.SectionedMovie

data class HomeMoviesUiState(
    val isTryingToSetup: Boolean,
    val movies: List<SectionedMovie>,
    val error: Error?,
    val navigate: Navigate?
) {

    sealed class Error {
        class ErrorOnSetup(val message: String) : Error()
        class ErrorOnOpeningMovie(val movie: MoviePreview, val message: String) : Error()
    }

    sealed class Navigate {
        data class Details(val movie: MoviePreview) : Navigate()
    }

    companion object {
        val DEFAULT = HomeMoviesUiState(
            isTryingToSetup = true,
            movies = listOf(),
            error = null,
            navigate = null
        )
    }
}
