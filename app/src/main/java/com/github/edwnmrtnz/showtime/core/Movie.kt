package com.github.edwnmrtnz.showtime.core

data class Movie(
    val id: Int,
    val title: String,
    val thumbnail: String,
    val overview: String,
    val keywords: List<String>,
    val cast: List<Cast>
) {
    data class Cast(val thumbnail: String?, val name: String) {
        fun hasPhoto(): Boolean = !thumbnail.isNullOrBlank()
    }

    companion object {
        fun dummy(id: Int = 1): Movie {
            return Movie(
                id = id,
                title = "Avengers: Endgame",
                thumbnail = "",
                overview = "Sample Overview",
                keywords = listOf("Action", "Fantasy"),
                cast = listOf(
                    Cast("", "Maviz Vermillion"),
                    Cast("", "Natsu Dragneel"),
                    Cast("", "Natsu Dragneel"),
                    Cast("", "Natsu Dragneel")
                )
            )
        }
    }
}
