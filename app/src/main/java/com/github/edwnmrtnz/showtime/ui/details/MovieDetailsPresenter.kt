package com.github.edwnmrtnz.showtime.ui.details

import com.github.edwnmrtnz.showtime.core.MoviesRepository
import com.github.edwnmrtnz.showtime.core.ShowtimeException
import com.github.edwnmrtnz.showtime.ui.helpers.presenter.StatefulPresenter
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class MovieDetailsPresenter @AssistedInject constructor(
    private val repository: MoviesRepository,
    @Assisted val movieId: Int

) : StatefulPresenter<MovieDetailsUiState, MovieDetailsView>() {

    override fun initialState(): MovieDetailsUiState = MovieDetailsUiState.DEFAULT

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

    @AssistedFactory
    interface Factory {
        fun create(@Assisted movieId: Int): MovieDetailsPresenter
    }
}
