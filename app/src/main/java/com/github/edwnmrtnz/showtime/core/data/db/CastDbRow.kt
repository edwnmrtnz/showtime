package com.github.edwnmrtnz.showtime.core.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "movie_casts",
    primaryKeys = ["movie_id", "name"]
)
data class CastDbRow(
    @ColumnInfo(name = "movie_id")
    val movieId: Int,

    @ColumnInfo(name = "photo")
    val photo: String?,

    @ColumnInfo(name = "name")
    val name: String
)
