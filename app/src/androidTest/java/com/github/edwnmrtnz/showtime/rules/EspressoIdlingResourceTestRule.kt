package com.github.edwnmrtnz.showtime.rules

import androidx.test.espresso.IdlingRegistry
import com.github.edwnmrtnz.showtime.app.helper.EspressoIdlingResource
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class EspressoIdlingResourceTestRule : TestWatcher() {
    override fun starting(description: Description) {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        super.starting(description)
    }

    override fun finished(description: Description) {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        super.finished(description)
    }
}
