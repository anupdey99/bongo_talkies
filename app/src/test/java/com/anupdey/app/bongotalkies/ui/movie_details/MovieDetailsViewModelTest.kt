package com.anupdey.app.bongotalkies.ui.movie_details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.anupdey.app.bongotalkies.MainDispatcherRule
import com.anupdey.app.bongotalkies.data.repository.FakeMovieRepository
import com.anupdey.app.bongotalkies.ui.movie_details.ViewState
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MovieDetailsViewModelTest {

    private lateinit var viewModel: MovieDetailsViewModel

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        viewModel = MovieDetailsViewModel(FakeMovieRepository())
    }

    @Test
    fun fetchMovieDetails_flowSuccess() = runBlocking {

        val job = launch {
            viewModel.viewStateEvent.test {
                val emission1 = awaitItem()
                if (emission1 is ViewState.ProgressState) {
                    Truth.assertThat("Loading").isEqualTo("Loading")
                }
                val emission2 = awaitItem()
                if (emission2 is ViewState.InitData) {
                    Truth.assertThat("Loading").isEqualTo("Loading")
                }
                cancelAndConsumeRemainingEvents()
            }
        }
        viewModel.fetchMovieDetails(1)
        job.join()
        job.cancel()
    }
}