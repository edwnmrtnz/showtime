package com.github.edwnmrtnz.showtime.core.data

import com.github.edwnmrtnz.showtime.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class TMDBInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder()
            .header("Authorization", "Bearer ${BuildConfig.TMDB_TOKEN}")
            .build()
        return chain.proceed(request)
    }
}
