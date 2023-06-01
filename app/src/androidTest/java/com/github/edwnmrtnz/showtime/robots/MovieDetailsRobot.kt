package com.github.edwnmrtnz.showtime.robots

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.github.edwnmrtnz.showtime.R

class MovieDetailsRobot {

    fun shouldHaveThePhoto(): MovieDetailsRobot {
        onView(withId(R.id.ivMovieBanner))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        return this
    }

    fun shouldHaveTheTitle(): MovieDetailsRobot {
        onView(withId(R.id.tvMovieTitle))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        return this
    }

    fun shouldHaveTheGenres(): MovieDetailsRobot {
        onView(withId(R.id.cgKeywords))
            .check(ViewAssertions.matches(ViewMatchers.hasMinimumChildCount(1)))
        return this
    }

    fun shouldHaveTheOverview(): MovieDetailsRobot {
        onView(withId(R.id.tvMovieOverview))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        return this
    }

    fun shouldHaveTheCast(): MovieDetailsRobot {
        onView(withId(R.id.rvMovieCasts))
            .check(ViewAssertions.matches(ViewMatchers.hasMinimumChildCount(1)))
        return this
    }
}
