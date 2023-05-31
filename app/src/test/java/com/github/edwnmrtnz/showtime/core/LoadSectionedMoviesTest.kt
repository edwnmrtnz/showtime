package com.github.edwnmrtnz.showtime.core

import androidx.test.core.app.ApplicationProvider
import com.github.edwnmrtnz.showtime.RoboTest
import com.github.edwnmrtnz.showtime.TestAssetReader
import com.github.edwnmrtnz.showtime.core.data.db.MoviesDao
import com.github.edwnmrtnz.showtime.provideMoviesDb
import com.github.edwnmrtnz.showtime.provideMoviesRepo
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

class LoadSectionedMoviesTest : RoboTest() {

    private lateinit var server: MockWebServer
    private lateinit var repo: MoviesRepository
    private lateinit var dao: MoviesDao

    @Before
    fun setup() {
        server = MockWebServer()

        dao = provideMoviesDb(ApplicationProvider.getApplicationContext()).moviesDao()
        repo = provideMoviesRepo(server.url("/"), dao)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `must be able to fetch and save movie sectioned movies`() = runTest {
        listOf("popular.json", "now_playing.json", "top_rated.json", "upcoming.json").forEach {
            val raw = TestAssetReader.read("app", it)
            val response = MockResponse()
                .setResponseCode(200)
                .setBody(raw)
            server.enqueue(response)
        }

        repo.load()

        Truth.assertThat(dao.getSectionedMovies()).isNotEmpty()
    }
}
