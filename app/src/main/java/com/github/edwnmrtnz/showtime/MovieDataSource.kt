package com.github.edwnmrtnz.showtime

object MovieDataSource {
    val items = listOf(
        SectionedMovie("Trending Now", Array(50) { Movie.dummy() }.toList()),
        SectionedMovie("New Releases", Array(50) { Movie.dummy() }.toList()),
        SectionedMovie("Action", Array(50) { Movie.dummy() }.toList()),
        SectionedMovie("Drama", Array(50) { Movie.dummy() }.toList())
    )
}