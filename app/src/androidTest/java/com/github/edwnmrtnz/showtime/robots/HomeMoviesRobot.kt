package com.github.edwnmrtnz.showtime.robots

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.github.edwnmrtnz.showtime.R
import com.github.edwnmrtnz.showtime.core.MovieSection

class HomeMoviesRobot {

    fun hasThePopularSection(): HomeMoviesRobot {
        onView(ViewMatchers.withText(MovieSection.Popular.name))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        return this
    }

    fun hasTheNowPlayingSection(): HomeMoviesRobot {
        onView(ViewMatchers.withText(MovieSection.NowPlaying.name))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        return this
    }

    fun hasTheUpcomingSection(): HomeMoviesRobot {
        val scrollTo = RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
            ViewMatchers.hasDescendant(ViewMatchers.withText(MovieSection.Upcoming.name))
        )
        onView(ViewMatchers.withId(R.id.rvSectionedMovies))
            .perform(scrollTo)
        onView(ViewMatchers.withText(MovieSection.Upcoming.name))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        return this
    }

    fun hasTheTopRatedSection(): HomeMoviesRobot {
        onView(ViewMatchers.withText(MovieSection.TopRated.name))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        return this
    }

    fun clickFirstMovie(): MovieDetailsRobot {
        val clickFirstAction =
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        onView(ViewMatchers.withId(R.id.rvSectionedMovies))
            .perform(clickFirstAction)
        return MovieDetailsRobot()
    }
}
