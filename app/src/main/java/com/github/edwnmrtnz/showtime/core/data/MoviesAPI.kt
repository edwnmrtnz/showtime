package com.github.edwnmrtnz.showtime.core.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface MoviesAPI {

    @GET("3/discover/movie")
    suspend fun getPopular(@QueryMap params: HashMap<String, String>): MoviesResponse

    @GET("3/movie/now_playing")
    suspend fun getNowPlaying(@QueryMap params: HashMap<String, String>): MoviesResponse

    @GET("3/movie/top_rated")
    suspend fun getTopRated(@QueryMap params: HashMap<String, String>): MoviesResponse

    @GET("3/movie/upcoming")
    suspend fun getUpcoming(@QueryMap params: HashMap<String, String>): MoviesResponse

    @GET("https://api.themoviedb.org/3/movie/{movie_id}?language=en-US&append_to_response=casts")
    suspend fun get(@Path("movie_id") id: Int): MovieResponse
}
