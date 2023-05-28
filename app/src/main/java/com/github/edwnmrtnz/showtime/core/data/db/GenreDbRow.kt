package com.github.edwnmrtnz.showtime.core.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "movie_genre",
    primaryKeys = ["movie_id", "genre"]
)
class GenreDbRow(
    @ColumnInfo(name = "movie_id")
    val movieId: Int,
    @ColumnInfo(name = "genre")
    val genre: String
)
