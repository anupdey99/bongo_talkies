package com.anupdey.app.bongotalkies.ui.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anupdey.app.bongotalkies.data.repository.MovieRepository
import com.anupdey.app.bongotalkies.util.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: MovieRepository
): ViewModel() {

    private val _viewState: MutableLiveData<ViewState> = MutableLiveData()
    val viewState: LiveData<ViewState> = _viewState

    fun fetchMovieDetails(id: Int) {
        viewModelScope.launch {
            repository.fetchMovieDetails(id).collect { result ->
                Timber.d("debugViewModel $result")
                when (result) {
                    is Resource.Error -> {
                        _viewState.value = ViewState.ProgressState(false)
                        _viewState.value = ViewState.ShowMessage(result.error!!.message)
                    }
                    is Resource.Loading -> {
                        _viewState.value = ViewState.ProgressState(true)
                    }
                    is Resource.Success -> {
                        val response = result.data!!
                        Timber.d("debugViewModel $response")
                        _viewState.value = ViewState.ProgressState(false)
                        _viewState.value = ViewState.InitData(response)
                    }
                }
            }
        }
    }
}