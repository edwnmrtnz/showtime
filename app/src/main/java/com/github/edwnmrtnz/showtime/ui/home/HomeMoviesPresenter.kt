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
                        isLoading = false,
                        dialog = null,
                        error = false
                    )
                }
            } catch (exception: ShowtimeException) {
                render { state ->
                    state.copy(
                        isLoading = false,
                        error = true,
                        dialog = HomeMoviesUiState.Dialog.ErrorOnSetup(exception.message)
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
                        dialog = HomeMoviesUiState.Dialog.ErrorOnOpeningMovie(
                            movie,
                            exception.message
                        ),
                        error = true
                    )
                }
            }
        }
    }

    fun onRetryLoading() {
        render { it.copy(isLoading = true, error = false, dialog = null) }
        setup()
    }

    fun onRetryOpeningMovie(movie: MoviePreview) {
        render { it.copy(isLoading = false, error = false, dialog = null) }
        loadMovieThenNavigate(movie)
    }

    fun onDialogDisplayed() {
        render { it.copy(dialog = null) }
    }

    fun onNavigationHandled() {
        render { it.copy(navigate = null) }
    }
}
