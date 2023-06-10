package com.github.edwnmrtnz.showtime.core.data

import android.util.SparseArray
import androidx.core.util.containsKey
import com.github.edwnmrtnz.showtime.app.di.IODispatcher
import com.github.edwnmrtnz.showtime.core.Movie
import com.github.edwnmrtnz.showtime.core.MoviePreview
import com.github.edwnmrtnz.showtime.core.MovieSection
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
                    async { api.getPopular(params).toSectionedMovies(MovieSection.Popular.name) },
                    async {
                        api.getNowPlaying(params).toSectionedMovies(
                            MovieSection.NowPlaying.name
                        )
                    },
                    async { api.getTopRated(params).toSectionedMovies(MovieSection.TopRated.name) },
                    async { api.getUpcoming(params).toSectionedMovies(MovieSection.Upcoming.name) }
                ).awaitAll().also { sv -> save(sv) }
            } catch (exception: HttpException) {
                exception.printStackTrace()
                throw ShowtimeException.HttpException(
                    code = exception.code(),
                    message = exception.message(),
                    exception = exception
                )
            } catch (exception: IOException) {
                exception.printStackTrace()
                throw ShowtimeException.IOException(exception)
            }
        }
    }

    private suspend fun save(sv: List<SectionedMovie>) {
        sv.forEach {
            val section = it.section
            val movies = it.movies
            movies.forEach { movie ->
                dao.saveSectionedMovie(SectionedMovieDbRow(section, movie.id, movie.thumbnail))
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
                throw ShowtimeException.HttpException(
                    exception.code(),
                    exception.message(),
                    exception
                )
            } catch (exception: IOException) {
                throw ShowtimeException.IOException(exception)
            }
        }
    }
}
