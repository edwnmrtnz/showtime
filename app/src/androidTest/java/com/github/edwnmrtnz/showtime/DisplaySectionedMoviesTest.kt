package com.github.edwnmrtnz.showtime

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.edwnmrtnz.showtime.app.MainActivity
import com.github.edwnmrtnz.showtime.robots.HomeMoviesRobot
import com.github.edwnmrtnz.showtime.rules.EspressoIdlingResourceTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DisplaySectionedMoviesTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val espressoIdlingResourceTestRule = EspressoIdlingResourceTestRule()

    @Test
    fun shouldHaveAllTheMovieSections() {
        HomeMoviesRobot()
            .hasThePopularSection()
            .hasTheNowPlayingSection()
            .hasTheTopRatedSection()
            .hasTheUpcomingSection()
    }
}
