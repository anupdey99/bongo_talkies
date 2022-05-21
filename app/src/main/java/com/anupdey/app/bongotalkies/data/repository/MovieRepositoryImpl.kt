package com.anupdey.app.bongotalkies.data.repository

import com.anupdey.app.bongotalkies.data.remote.BongoAPI
import com.anupdey.app.bongotalkies.data.remote.models.movie.MovieResponse
import com.anupdey.app.bongotalkies.data.remote.models.movie_details.MovieDetailsResponse
import com.anupdey.app.bongotalkies.util.network.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val apiInterface: BongoAPI
): MovieRepository {

    override suspend fun fetchTopRatedMovie(page: Int): Flow<Resource<MovieResponse>> {
        return flow {
            emit(Resource.Loading(null))
            when (val response = apiCall { apiInterface.fetchTopRatedMovie(page) }) {
                is ApiResponse.Error -> {
                    emit(Resource.Error(response.error!!))
                }
                is ApiResponse.Success -> {
                    if (response.data != null) {
                        emit(Resource.Success(response.data))
                    } else {
                        emit(Resource.Error(ApiError(ErrorType.FAILURE, "Something went wrong")))
                    }
                }
            }
        }
    }

    override suspend fun fetchMovieDetails(id: Int): Flow<Resource<MovieDetailsResponse>> {
        return flow {
            emit(Resource.Loading(null))
            when (val response = apiCall { apiInterface.fetchMovieDetails(id) }) {
                is ApiResponse.Error -> {
                    emit(Resource.Error(response.error!!))
                }
                is ApiResponse.Success -> {
                    if (response.data != null) {
                        emit(Resource.Success(response.data))
                    } else {
                        emit(Resource.Error(ApiError(ErrorType.FAILURE, "Something went wrong")))
                    }
                }
            }
        }
    }

}