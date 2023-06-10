package com.github.edwnmrtnz.showtime.ui.details

import com.github.edwnmrtnz.showtime.core.Movie

data class MovieDetailsUiState(
    val isLoading: Boolean = true,
    val movie: Movie? = null,
    val error: String? = null
) {
    fun requireMovie() = movie!!
    fun hasError() = !error.isNullOrBlank()
    fun requireError() = error!!
}
