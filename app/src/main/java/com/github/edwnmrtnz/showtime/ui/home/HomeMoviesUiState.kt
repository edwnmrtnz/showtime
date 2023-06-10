package com.github.edwnmrtnz.showtime.ui.home

import com.github.edwnmrtnz.showtime.core.MoviePreview
import com.github.edwnmrtnz.showtime.core.SectionedMovie

data class HomeMoviesUiState(
    val isTryingToSetup: Boolean = true,
    val movies: List<SectionedMovie> = listOf(),
    val error: Error? = null,
    val navigate: Navigate? = null
) {

    sealed class Error {
        class ErrorOnSetup(val message: String) : Error()
        class ErrorOnOpeningMovie(val movie: MoviePreview, val message: String) : Error()
    }

    sealed class Navigate {
        data class Details(val movie: MoviePreview) : Navigate()
    }
}
