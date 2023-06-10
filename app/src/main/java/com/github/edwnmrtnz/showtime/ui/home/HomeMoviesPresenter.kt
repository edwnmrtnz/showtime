package com.github.edwnmrtnz.showtime.ui.home

import com.github.edwnmrtnz.showtime.core.MoviePreview
import com.github.edwnmrtnz.showtime.core.MoviesRepository
import com.github.edwnmrtnz.showtime.core.ShowtimeException
import com.github.edwnmrtnz.showtime.ui.helpers.presenter.StatefulPresenter
import javax.inject.Inject
import kotlinx.coroutines.launch

class HomeMoviesPresenter @Inject constructor(
    private val movieRepository: MoviesRepository
) : StatefulPresenter<HomeMoviesUiState, HomeMoviesView>() {

    override fun initialState(): HomeMoviesUiState = HomeMoviesUiState()

    override fun onCreated(state: HomeMoviesUiState) {
        setup()
    }

    private fun setup() {
        scope.launch {
            try {
                val movies = movieRepository.load()
                render { state ->
                    state.copy(
                        movies = movies,
                        isTryingToSetup = false
                    )
                }
            } catch (exception: ShowtimeException) {
                render { state ->
                    state.copy(
                        isTryingToSetup = false,
                        error = HomeMoviesUiState.Error.ErrorOnSetup(exception.message)
                    )
                }
            }
        }
    }

    fun onItemClicked(movie: MoviePreview) {
        loadMovieThenNavigate(movie)
    }

    private fun loadMovieThenNavigate(movie: MoviePreview) {
        scope.launch {
            try {
                movieRepository.load(movie.id)
                render { it.copy(navigate = HomeMoviesUiState.Navigate.Details(movie)) }
            } catch (exception: ShowtimeException) {
                render {
                    it.copy(
                        error = HomeMoviesUiState.Error.ErrorOnOpeningMovie(
                            movie,
                            exception.message
                        )
                    )
                }
            }
        }
    }

    fun onRetryLoading() {
        render { it.copy(isTryingToSetup = true, error = null) }
        setup()
    }

    fun onRetryOpeningMovie(movie: MoviePreview) {
        render { it.copy(error = null) }
        loadMovieThenNavigate(movie)
    }

    fun onErrorHandled() {
        render { it.copy(error = null) }
    }

    fun onNavigationHandled() {
        render { it.copy(navigate = null) }
    }
}
