package com.github.edwnmrtnz.showtime

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.github.edwnmrtnz.showtime.core.data.api.MovieResponse
import com.github.edwnmrtnz.showtime.core.data.db.MoviesDatabase
import com.github.edwnmrtnz.showtime.core.data.toDb
import com.google.common.truth.Truth
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [Config.OLDEST_SDK], application = Application::class)
class MoviesSavingAndQueryTest {

    private val raw = TestAssetReader.read("app", "details.json")
    private lateinit var database: MoviesDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            MoviesDatabase::class.java
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
    }

    @Test
    fun `should be able to save and query movie`() = runBlocking {
        val movie = Gson().fromJson(raw, MovieResponse::class.java)
        val dao = database.dao()

        dao.saveMovie(movie.toDb())
        val result = dao.get(movie.id)

        Truth.assertThat(result).isNotNull()
    }
}
