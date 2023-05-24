package com.github.edwnmrtnz.showtime.core.data

import android.util.Log
import android.util.SparseArray
import androidx.core.util.containsKey
import com.github.edwnmrtnz.showtime.app.di.IODispatcher
import com.github.edwnmrtnz.showtime.app.helper.ApiQueryParamsBuilder
import com.github.edwnmrtnz.showtime.core.Movie
import com.github.edwnmrtnz.showtime.core.MoviesRepository
import com.github.edwnmrtnz.showtime.core.SectionedMovie
import com.github.edwnmrtnz.showtime.core.ShowtimeException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException

@Singleton
class DefaultMoviesRepository @Inject constructor(
    @IODispatcher private val dispatcher: CoroutineDispatcher,
    private val api: MoviesAPI
) : MoviesRepository {
    companion object {
        private const val TAG = "DefaultMoviesRepository"
    }

    init {
        Log.e(TAG, "initialized")
    }

    private val movies: SparseArray<Movie> = SparseArray()

    override suspend fun load(): List<SectionedMovie> {
        return withContext(dispatcher) {
            val params = ApiQueryParamsBuilder().page(1).build()
            try {
                val popular = api.getPopular(params).toSectionedMovies("Popular")
                val now = api.getNowPlaying(params).toSectionedMovies("Now Playing")
                val top = api.getTopRated(params).toSectionedMovies("Top Rated")
                val upcoming = api.getUpcoming(params).toSectionedMovies("Upcoming")
                listOf(popular, top, upcoming, now)
            } catch (exception: HttpException) {
                throw ShowtimeException.HttpException(exception.code(), exception.message())
            } catch (exception: IOException) {
                throw ShowtimeException.IOException(exception)
            }
        }
    }

    override suspend fun load(id: Int): Movie {
        if (movies.containsKey(id)) return movies.get(id)
        return withContext(dispatcher) {
            try {
                api.get(id).toMovie().also { movie -> movies.put(movie.id, movie) }
            } catch (exception: HttpException) {
                Log.e(TAG, "load for $id: $exception")
                throw ShowtimeException.HttpException(exception.code(), exception.message())
            } catch (exception: IOException) {
                throw ShowtimeException.IOException(exception)
            }
        }
    }
}
