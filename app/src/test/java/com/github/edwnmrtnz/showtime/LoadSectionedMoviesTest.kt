package com.github.edwnmrtnz.showtime

import android.app.Application
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.github.edwnmrtnz.showtime.core.MoviesRepository
import com.github.edwnmrtnz.showtime.core.data.DefaultMoviesRepository
import com.github.edwnmrtnz.showtime.core.data.api.MoviesAPI
import com.github.edwnmrtnz.showtime.core.data.db.MoviesDao
import com.github.edwnmrtnz.showtime.core.data.db.MoviesDatabase
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [Config.OLDEST_SDK], application = Application::class)
class LoadSectionedMoviesTest {

    private lateinit var server: MockWebServer
    private lateinit var repo: MoviesRepository
    private lateinit var dao: MoviesDao

    @Before
    fun setup() {
        server = MockWebServer()
        dao = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MoviesDatabase::class.java
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
            .dao()
        repo = DefaultMoviesRepository(
            Dispatchers.Unconfined,
            Retrofit.Builder()
                .baseUrl(server.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MoviesAPI::class.java),
            dao
        )
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `must be able to fetch and save movie sectioned movies`() = runTest {
        val raw = TestAssetReader.read("app", "popular.json")
        val response = MockResponse()
            .setResponseCode(200)
            .setBody(raw)
        server.enqueue(response)
        server.enqueue(response)
        server.enqueue(response)
        server.enqueue(response)

        repo.load()

        Truth.assertThat(dao.getSectionedMovies()).isNotEmpty()
    }
}
