package com.anupdey.app.bongotalkies.data.repository

import com.anupdey.app.bongotalkies.data.remote.models.movie.MovieResponse
import com.anupdey.app.bongotalkies.data.remote.models.movie_details.MovieDetailsResponse
import com.anupdey.app.bongotalkies.util.network.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun fetchTopRatedMovie(page: Int): Flow<Resource<MovieResponse>>

    suspend fun fetchMovieDetails(id: Int): Flow<Resource<MovieDetailsResponse>>
}