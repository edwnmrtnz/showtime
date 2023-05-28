package com.github.edwnmrtnz.showtime.core.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "sectioned_movies",
    primaryKeys = ["movie_id", "section"]
)
data class SectionedMovieDbRow(
    @ColumnInfo("section")
    val section: String,
    @ColumnInfo("movie_id")
    val movieId: Int,
    @ColumnInfo("thumbnail")
    val thumbnail: String
)
