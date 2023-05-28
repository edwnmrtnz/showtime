package com.github.edwnmrtnz.showtime.core.data

import android.util.Log
import android.util.SparseArray
import androidx.core.util.containsKey
import com.github.edwnmrtnz.showtime.app.di.IODispatcher
import com.github.edwnmrtnz.showtime.app.helper.ApiQueryParamsBuilder
import com.github.edwnmrtnz.showtime.core.Movie
import com.github.edwnmrtnz.showtime.core.MoviePreview
import com.github.edwnmrtnz.showtime.core.MoviesRepository
import com.github.edwnmrtnz.showtime.core.SectionedMovie
import com.github.edwnmrtnz.showtime.core.ShowtimeException
import com.github.edwnmrtnz.showtime.core.data.api.MoviesAPI
import com.github.edwnmrtnz.showtime.core.data.db.MoviesDao
import com.github.edwnmrtnz.showtime.core.data.db.SectionedMovieDbRow
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import retrofit2.HttpException

@Singleton
class DefaultMoviesRepository @Inject constructor(
    @IODispatcher private val dispatcher: CoroutineDispatcher,
    private val api: MoviesAPI,
    private val dao: MoviesDao
) : MoviesRepository {

    companion object {
        private const val TAG = "DefaultMoviesRepository"
    }

    private sealed class Section(val name: String) {
        object Popular : Section("Popular")
        object NowPlaying : Section("Now PLaying")
        object TopRated : Section("Top Rated")
        object Upcoming : Section("Upcoming")
    }

    init {
        Log.e(TAG, "initialized default movies repository")
    }

    private val movies: SparseArray<Movie> = SparseArray()

    override suspend fun load(): List<SectionedMovie> {
        return withContext(dispatcher) {
            val sectioned = dao
                .getSectionedMovies()
                .groupBy { it.section }
                .map {
                    SectionedMovie(
                        it.key,
                        it.value.map { m -> MoviePreview(m.movieId, m.thumbnail) }
                    )
                }
            if (sectioned.isNotEmpty()) return@withContext sectioned
            val params = ApiQueryParamsBuilder().page(1).build()
            try {
                listOf(
                    async { api.getPopular(params).toSectionedMovies(Section.Popular.name) },
                    async { api.getNowPlaying(params).toSectionedMovies(Section.NowPlaying.name) },
                    async { api.getTopRated(params).toSectionedMovies(Section.TopRated.name) },
                    async { api.getUpcoming(params).toSectionedMovies(Section.Upcoming.name) }
                ).awaitAll().also { sv -> save(sv) }
            } catch (exception: HttpException) {
                throw ShowtimeException.HttpException(exception.code(), exception.message())
            } catch (exception: IOException) {
                throw ShowtimeException.IOException(exception)
            }
        }
    }

    private fun save(sv: List<SectionedMovie>) {
        sv.forEach {
            val section = it.section
            val movies = it.movies
            movies.forEach { movie ->
                SectionedMovieDbRow(section, movie.id, movie.thumbnail)
            }
        }
    }

    override suspend fun load(id: Int): Movie {
        if (movies.containsKey(id)) return movies.get(id)
        return withContext(dispatcher) {
            val row = dao.get(id)
            if (row != null) return@withContext row.toMovie()
            try {
                val raw = api.get(id)
                dao.saveMovie(raw.toDb())
                raw.toMovie().also { movie -> movies.put(movie.id, movie) }
            } catch (exception: HttpException) {
                throw ShowtimeException.HttpException(exception.code(), exception.message())
            } catch (exception: IOException) {
                throw ShowtimeException.IOException(exception)
            }
        }
    }
}
