package com.github.edwnmrtnz.showtime.app

import com.github.edwnmrtnz.showtime.core.SectionedMovie
import com.github.edwnmrtnz.showtime.core.ShowtimeException
import com.github.edwnmrtnz.showtime.core.data.FakeMoviesRepository
import com.github.edwnmrtnz.showtime.ui.helpers.presenter.StatefulPresenter
import com.github.edwnmrtnz.showtime.ui.helpers.presenter.TestView
import com.github.edwnmrtnz.showtime.ui.home.HomeMoviesPresenter
import com.github.edwnmrtnz.showtime.ui.home.HomeMoviesUiState
import com.github.edwnmrtnz.showtime.ui.home.HomeMoviesView
import com.google.common.truth.Truth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class HomeMoviesPresenterTest {

    class FailingMoviesRepository : FakeMoviesRepository() {
        override suspend fun load(): List<SectionedMovie> {
            throw ShowtimeException
                .HttpException(404, "error", Exception("error"))
        }
    }

    class FakeView : TestView<HomeMoviesUiState>(), HomeMoviesView

    private val view = FakeView()
    private lateinit var sut: HomeMoviesPresenter

    @Before
    fun setup() {
        StatefulPresenter.setScope(CoroutineScope(Dispatchers.Unconfined))
        sut = HomeMoviesPresenter(FailingMoviesRepository())
    }

    @Test
    fun `given will fail to load, when bind, then show error`() = runBlocking {
        sut.bind(view)

        Truth.assertThat(view.state().error).isNotNull()
        Truth.assertThat(view.state().error).isInstanceOf(
            HomeMoviesUiState.Error.ErrorOnSetup::class.java
        )
    }

    @Test
    fun `given will fail to load, when bind, then no loading`() = runBlocking {
        sut.bind(view)

        Truth.assertThat(view.state().isTryingToSetup).isFalse()
    }
}
