package com.anupdey.app.bongotalkies.ui.home

import com.anupdey.app.bongotalkies.data.remote.models.movie.MovieData

sealed class ViewState {
    //object NONE : ViewState()
    data class ShowMessage(val message: String?, val type: Int = 0) : ViewState()
    data class ProgressState(val isShow: Boolean = false, val type: Int = 0) : ViewState()
    data class EmptyState(val isShow: Boolean = false, val type: Int = 0) : ViewState()
    data class InitData(val list: List<MovieData>, val currentPage: Int, val totalPage: Int): ViewState()
}
