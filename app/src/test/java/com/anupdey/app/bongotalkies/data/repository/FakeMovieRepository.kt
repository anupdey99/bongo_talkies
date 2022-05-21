package com.anupdey.app.bongotalkies.data.repository

import com.anupdey.app.bongotalkies.data.remote.models.movie.MovieData
import com.anupdey.app.bongotalkies.data.remote.models.movie.MovieResponse
import com.anupdey.app.bongotalkies.data.remote.models.movie_details.MovieDetailsResponse
import com.anupdey.app.bongotalkies.util.network.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeMovieRepository: MovieRepository {

    private var isNetworkError: Boolean = false

    fun throughNetworkError(flag: Boolean) {
        isNetworkError = flag
    }

    override suspend fun fetchTopRatedMovie(page: Int): Flow<Resource<MovieResponse>> {
        return flow {
            emit(Resource.Loading(null))
            if (isNetworkError) {
                emit(Resource.Error(ApiError(ErrorType.FAILURE, "Something went wrong")))
            } else {
                val model = MovieResponse(
                    1,
                    1,
                    2,
                    List(3) {
                        val index = it + 1 
                        MovieData(id = index, title = "Movie$index")
                    }
                )
                emit(Resource.Success(model))
            }
        }
    }

    override suspend fun fetchMovieDetails(id: Int): Flow<Resource<MovieDetailsResponse>> {
        return flow {
            emit(Resource.Loading(null))
            if (isNetworkError) {
                emit(Resource.Error(ApiError(ErrorType.FAILURE, "Something went wrong")))
            } else {
                val index = 1
                val model = MovieDetailsResponse(
                    id = index,
                    title = "Movie$index"
                )
                emit(Resource.Success(model))
            }
        }
    }

}