package com.github.edwnmrtnz.showtime.app

import android.content.Context
import android.util.Log
import com.squareup.picasso.BuildConfig
import com.squareup.picasso.Picasso

object PicassoHelper {
    fun init(context: Context) {
        val picasso = Picasso.Builder(context.applicationContext)
            .listener { _, uri, exception ->
                Log.e("Picasso", "Failed to load $uri because $exception")
            }
            .loggingEnabled(BuildConfig.DEBUG)
            .indicatorsEnabled(BuildConfig.DEBUG)
            .build()
        Picasso.setSingletonInstance(picasso)
    }
}
