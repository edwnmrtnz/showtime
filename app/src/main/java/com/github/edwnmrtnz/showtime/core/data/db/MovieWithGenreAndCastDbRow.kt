package com.github.edwnmrtnz.showtime.core.data.db

import androidx.room.Embedded
import androidx.room.Relation

class MovieWithGenreAndCastDbRow(
    @Embedded
    val movie: MovieDbRow,
    @Relation(
        parentColumn = "id",
        entityColumn = "movie_id"
    )
    val casts: List<CastDbRow>,
    @Relation(
        parentColumn = "id",
        entityColumn = "movie_id"
    )
    val genres: List<GenreDbRow>
)
