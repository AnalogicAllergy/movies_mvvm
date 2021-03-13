package com.woken.moviesmvvm.data.api

import com.woken.moviesmvvm.data.vo.MovieDetails
import com.woken.moviesmvvm.data.vo.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDBInterface {
    @GET("movie/{movie_id}")
    //tells retrofit that our parameter id is the path equivalent in the get request - route param
    fun getMovieDetails(@Path("movie_id") id:Int):Single<MovieDetails>

    @GET("movie/popular")
    //&page=1
    fun getPopularMovie(@Query("page") page:Int):Single<MovieResponse>
}