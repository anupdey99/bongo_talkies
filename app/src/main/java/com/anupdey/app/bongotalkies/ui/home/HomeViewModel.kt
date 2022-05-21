package com.anupdey.app.bongotalkies.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anupdey.app.bongotalkies.data.remote.models.movie.MovieData
import com.anupdey.app.bongotalkies.data.remote.models.movie.MovieResponse
import com.anupdey.app.bongotalkies.data.repository.MovieRepository
import com.anupdey.app.bongotalkies.util.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
): ViewModel() {

    private val _viewState: MutableLiveData<ViewState> = MutableLiveData()
    val viewState: LiveData<ViewState> = _viewState
    private val movieList: MutableList<MovieData> = mutableListOf()
    private var currentPage: Int = 1
    private var totalPage: Int = -1
    private var totalCount: Int = -1
    private var isLoading = false
    private var visibleThreshold = 6

    init {
        fetchTopRatedMovie(1)
    }

    private fun fetchTopRatedMovie(page: Int) {
        viewModelScope.launch {
            isLoading = true
            repository.fetchTopRatedMovie(page).collect { result ->
                Timber.d("debugViewModel $result")
                when (result) {
                    is Resource.Error -> {
                        isLoading = false
                        _viewState.value = ViewState.ProgressState(false)
                        _viewState.value = ViewState.ShowMessage(result.error!!.message)
                    }
                    is Resource.Loading -> {
                        _viewState.value = ViewState.ProgressState(true)
                    }
                    is Resource.Success -> {
                        isLoading = false
                        val response = result.data!!
                        Timber.d("debugViewModel $response")
                        currentPage = response.page
                        totalPage = response.totalPages
                        totalCount = response.totalResults
                        _viewState.value = ViewState.ProgressState(false)
                        if (currentPage == 1) {
                            movieList.clear()
                            movieList.addAll(response.movieData)
                            _viewState.value = ViewState.EmptyState(movieList.isEmpty())
                        } else {
                            movieList.addAll(response.movieData)
                        }
                        _viewState.value = ViewState.InitData(movieList, currentPage, totalPage)
                    }
                }
            }
        }
    }

    fun loadMore(currentItemCount: Int, lastVisibleItem: Int) {
        if (!isLoading && currentItemCount <= lastVisibleItem + visibleThreshold && currentItemCount < totalCount) {
            fetchTopRatedMovie(currentPage + 1)
        }
    }

}