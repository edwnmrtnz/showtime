package com.github.edwnmrtnz.showtime.core.data

import com.github.edwnmrtnz.showtime.core.Movie
import com.github.edwnmrtnz.showtime.core.MoviePreview
import com.github.edwnmrtnz.showtime.core.SectionedMovie

object MovieDataSource {
    val items = listOf(
        SectionedMovie("Popular", Array(20) { MoviePreview.dummy(it + 1000) }.toList()),
        SectionedMovie("Now Playing", Array(50) { MoviePreview.dummy(it + 3000) }.toList()),
        SectionedMovie("Upcoming", Array(50) { MoviePreview.dummy(it + 5000) }.toList()),
        SectionedMovie("Top Rated", Array(50) { MoviePreview.dummy(it + 7000) }.toList())
    )
    val item = Movie.dummy()
}
