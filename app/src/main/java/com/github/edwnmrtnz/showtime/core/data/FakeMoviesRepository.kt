package com.github.edwnmrtnz.showtime.core.data

import com.github.edwnmrtnz.showtime.core.Movie
import com.github.edwnmrtnz.showtime.core.MoviesRepository
import com.github.edwnmrtnz.showtime.core.SectionedMovie
import javax.inject.Inject

open class FakeMoviesRepository @Inject constructor() : MoviesRepository {

    override suspend fun load(): List<SectionedMovie> {
        return MovieDataSource.items
    }

    override suspend fun load(id: Int): Movie {
        return MovieDataSource.item
    }
}
