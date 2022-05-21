package com.anupdey.app.bongotalkies.ui.movie_details

import com.anupdey.app.bongotalkies.data.remote.models.movie.MovieData
import com.anupdey.app.bongotalkies.data.remote.models.movie_details.MovieDetailsResponse
import com.anupdey.app.bongotalkies.util.network.ApiError

sealed class ViewState {
    data class ShowError(val error: ApiError) : ViewState()
    data class ProgressState(val isShow: Boolean = false, val type: Int = 0) : ViewState()
    data class EmptyState(val isShow: Boolean = false, val type: Int = 0) : ViewState()
    data class InitData(val model: MovieDetailsResponse): ViewState()
}
