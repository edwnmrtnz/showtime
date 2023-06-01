package com.github.edwnmrtnz.showtime.core

sealed class MovieSection(val name: String) {
    object Popular : MovieSection("Popular")
    object NowPlaying : MovieSection("Now Playing")
    object TopRated : MovieSection("Top Rated")
    object Upcoming : MovieSection("Upcoming")
}
