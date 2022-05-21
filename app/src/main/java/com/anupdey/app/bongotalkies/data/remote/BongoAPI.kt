package com.anupdey.app.bongotalkies.data.remote

import com.anupdey.app.bongotalkies.BuildConfig
import com.anupdey.app.bongotalkies.data.remote.models.movie.MovieResponse
import com.anupdey.app.bongotalkies.data.remote.models.movie_details.MovieDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BongoAPI {

    @GET("movie/top_rated")
    suspend fun fetchTopRatedMovie(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "en-US"
    ): MovieResponse

    @GET("movie/{id}")
    suspend fun fetchMovieDetails(
        @Path("id") id: Int ,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "en-US"
    ): MovieDetailsResponse

}