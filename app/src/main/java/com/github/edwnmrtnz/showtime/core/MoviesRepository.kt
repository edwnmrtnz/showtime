package com.github.edwnmrtnz.showtime.core

interface MoviesRepository {

    suspend fun load(): List<SectionedMovie>

    suspend fun load(id: Int): Movie
}
