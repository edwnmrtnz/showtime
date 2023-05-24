package com.github.edwnmrtnz.showtime.core

data class SectionedMovie(
    val section: String,
    val movies: List<MoviePreview>
)
