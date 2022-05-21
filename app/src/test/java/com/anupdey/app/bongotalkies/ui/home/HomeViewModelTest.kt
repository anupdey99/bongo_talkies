package com.anupdey.app.bongotalkies.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import app.cash.turbine.test
import com.anupdey.app.bongotalkies.MainDispatcherRule
import com.anupdey.app.bongotalkies.data.repository.FakeMovieRepository
import com.anupdey.app.bongotalkies.getOrAwaitValueTest
import org.junit.Before
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        viewModel = HomeViewModel(FakeMovieRepository())
    }

    @After
    fun tearDown() {
        viewModel.viewModelScope.cancel()
    }

    @Test
    fun fetchTopRatedMovie_flowSuccess() = runBlocking {

        val job = launch {
            viewModel.viewStateEvent.test {
                val emission1 = awaitItem()
                if (emission1 is ViewState.ProgressState) {
                    assertThat("Loading").isEqualTo("Loading")
                }
                val emission2 = awaitItem()
                if (emission2 is ViewState.InitData) {
                    assertThat("Loading").isEqualTo("Loading")
                }
                cancelAndConsumeRemainingEvents()
            }
        }
        viewModel.fetchTopRatedMovie(1)
        job.join()
        job.cancel()
    }
}