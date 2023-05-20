package com.github.edwnmrtnz.showtime.core

import com.github.edwnmrtnz.showtime.ui.home.SectionedMovie

object MovieDataSource {
    val items = listOf(
        SectionedMovie("Popular", Array(20) { Movie.dummy(it + 1000) }.toList()),
        SectionedMovie("Now Playing", Array(50) { Movie.dummy(it + 3000) }.toList()),
        SectionedMovie("Upcoming", Array(50) { Movie.dummy(it + 5000) }.toList()),
        SectionedMovie("Top Rated", Array(50) { Movie.dummy(it + 7000) }.toList())
    )
}