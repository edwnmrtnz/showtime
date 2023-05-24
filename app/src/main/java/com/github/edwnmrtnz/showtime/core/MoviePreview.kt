package com.github.edwnmrtnz.showtime.core

data class MoviePreview(
    val id: Int,
    val thumbnail: String
) {
    companion object {
        fun dummy(id: Int = 1, thumbnail: String = "https://fakeimg.pl/170x220"): MoviePreview {
            return MoviePreview(id, thumbnail)
        }
    }
}
