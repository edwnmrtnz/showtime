package com.github.edwnmrtnz.showtime

data class Movie(
    val thumbnail: String
) {
    companion object {
        fun dummy(): Movie {
            return Movie("Avengers: Endgame")
        }
    }
}