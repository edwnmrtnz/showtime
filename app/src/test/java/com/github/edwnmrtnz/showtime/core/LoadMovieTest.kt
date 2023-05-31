package com.github.edwnmrtnz.showtime.core

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.github.edwnmrtnz.showtime.RoboTest
import com.github.edwnmrtnz.showtime.TestAssetReader
import com.github.edwnmrtnz.showtime.core.data.api.MovieResponse
import com.github.edwnmrtnz.showtime.core.data.db.MoviesDatabase
import com.github.edwnmrtnz.showtime.core.data.toDb
import com.github.edwnmrtnz.showtime.provideMoviesDb
import com.google.common.truth.Truth
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class LoadMovieTest : RoboTest() {

    private val raw = TestAssetReader.read("app", "details.json")
    private lateinit var database: MoviesDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = provideMoviesDb(context)
    }

    @Test
    fun `should be able to save and query movie`() = runBlocking {
        val movie = Gson().fromJson(raw, MovieResponse::class.java)
        val dao = database.moviesDao()

        dao.saveMovie(movie.toDb())
        val result = dao.get(movie.id)

        Truth.assertThat(result).isNotNull()
    }
}
