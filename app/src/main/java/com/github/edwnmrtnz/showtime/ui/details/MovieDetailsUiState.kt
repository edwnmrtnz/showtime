package com.github.edwnmrtnz.showtime.ui.details

import com.github.edwnmrtnz.showtime.core.Movie

data class MovieDetailsUiState(
    val isLoading: Boolean,
    val movie: Movie?,
    val error: String?
) {
    fun requireMovie() = movie!!

    companion object {
        val DEFAULT = MovieDetailsUiState(
            isLoading = true,
            movie = null,
            error = null
        )
    }
}
