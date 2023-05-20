package com.github.edwnmrtnz.showtime.ui.home

import com.github.edwnmrtnz.showtime.core.Movie

data class SectionedMovie (
    val section : String,
    val movies : List<Movie>
)