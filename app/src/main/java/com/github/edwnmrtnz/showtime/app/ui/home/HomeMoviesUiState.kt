package com.github.edwnmrtnz.showtime.app.ui.home

import com.github.edwnmrtnz.showtime.core.MoviePreview
import com.github.edwnmrtnz.showtime.core.SectionedMovie

data class HomeMoviesUiState(
    val isLoading: Boolean = true,
    val movies: List<SectionedMovie> = listOf(),
    val navigate: Navigate? = null,
    val dialog: Dialog? = null,
    val error: Boolean = false
) {
    sealed class Dialog {
        class ErrorOnSetup(val message: String) : Dialog()
        class ErrorOnOpeningMovie(val movie: MoviePreview, val message: String) : Dialog()
    }
    sealed class Navigate {
        data class Details(val movie: MoviePreview) : Navigate()
    }
}
