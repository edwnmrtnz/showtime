package com.github.edwnmrtnz.showtime.ui.details

import com.github.edwnmrtnz.showtime.core.MoviesRepository
import com.github.edwnmrtnz.showtime.core.ShowtimeException
import com.github.edwnmrtnz.showtime.ui.helpers.presenter.StatefulPresenter
import javax.inject.Inject
import kotlin.properties.Delegates
import kotlinx.coroutines.launch

class MovieDetailsPresenter @Inject constructor(
    private val repository: MoviesRepository

) : StatefulPresenter<MovieDetailsUiState, MovieDetailsView>() {

    private var movieId by Delegates.notNull<Int>()

    override fun initialState(): MovieDetailsUiState = MovieDetailsUiState()

    fun set(movieId: Int) {
        this.movieId = movieId
    }

    override fun onCreated(state: MovieDetailsUiState) {
        scope.launch {
            try {
                val movie = repository.load(movieId)
                render { it.copy(movie = movie, isLoading = false, error = null) }
            } catch (exception: ShowtimeException) {
                val message = "$exception:${exception.message}"
                render { it.copy(isLoading = false, error = message) }
            }
        }
    }
}
