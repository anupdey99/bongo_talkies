package com.anupdey.app.bongotalkies.ui.movie_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anupdey.app.bongotalkies.data.repository.MovieRepository
import com.anupdey.app.bongotalkies.util.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: MovieRepository
): ViewModel() {

    private var id = 0

    private val viewStateChannel = Channel<ViewState>()
    val viewStateEvent = viewStateChannel.receiveAsFlow()

    fun retry() {
        fetchMovieDetails(id)
    }

    fun fetchMovieDetails(id: Int) {
        this.id = id
        viewModelScope.launch {
            repository.fetchMovieDetails(id).collect { result ->
                Timber.d("debugViewModel $result")
                when (result) {
                    is Resource.Error -> {
                        viewStateChannel.send(ViewState.ProgressState(false))
                        viewStateChannel.send(ViewState.ShowError(result.error!!))
                    }
                    is Resource.Loading -> {
                        viewStateChannel.send(ViewState.ProgressState(true))
                    }
                    is Resource.Success -> {
                        val response = result.data!!
                        Timber.d("debugViewModel $response")
                        viewStateChannel.send(ViewState.ProgressState(false))
                        viewStateChannel.send(ViewState.InitData(response))
                    }
                }
            }
        }
    }
}