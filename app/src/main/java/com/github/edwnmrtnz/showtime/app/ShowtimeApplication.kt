package com.github.edwnmrtnz.showtime.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ShowtimeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        PicassoHelper.init(this)
    }
}
