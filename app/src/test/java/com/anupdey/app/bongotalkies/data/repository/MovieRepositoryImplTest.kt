package com.anupdey.app.bongotalkies.data.repository

import app.cash.turbine.test
import com.anupdey.app.bongotalkies.MainDispatcherRule
import com.anupdey.app.bongotalkies.util.network.Resource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule


class MovieRepositoryImplTest {

    private lateinit var repository: MovieRepository

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        repository = FakeMovieRepository()
    }

    @Test
    fun test_fetchTopRatedMovie() = runBlocking {
        repository.fetchTopRatedMovie(1).test {
            val emission1 = awaitItem()
            if (emission1 is Resource.Loading) {
                assertThat("Loading").isEqualTo("Loading")
            }
            val emission2 = awaitItem()
            if (emission2 is Resource.Success) {
                val currentPage = emission2.data?.page
                val totalPage = emission2.data?.totalPages
                val listSize = emission2.data?.movieData?.size
                assertThat(currentPage).isEqualTo(1)
                assertThat(totalPage).isEqualTo(1)
                assertThat(listSize).isEqualTo(3)
            }
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun test_fetchTopRatedMovie_wrongListSize() = runBlocking {
        repository.fetchTopRatedMovie(1).test {
            val emission1 = awaitItem()
            if (emission1 is Resource.Loading) {
                assertThat("Loading").isEqualTo("Loading")
            }
            val emission2 = awaitItem()
            if (emission2 is Resource.Success) {
                val currentPage = emission2.data?.page
                val totalPage = emission2.data?.totalPages
                val listSize = emission2.data?.movieData?.size
                assertThat(currentPage).isEqualTo(1)
                assertThat(totalPage).isEqualTo(1)
                assertThat(listSize).isNotEqualTo(0)
            }
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun test_fetchMovieDetails() = runBlocking {
        repository.fetchMovieDetails(1).test {
            val emission1 = awaitItem()
            if (emission1 is Resource.Loading) {
                assertThat("Loading").isEqualTo("Loading")
            }
            val emission2 = awaitItem()
            if (emission2 is Resource.Success) {
                val id = emission2.data?.id
                assertThat(id).isEqualTo(1)
            }
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun test_fetchMovieDetails_wrongId() = runBlocking {
        repository.fetchMovieDetails(1).test {
            val emission1 = awaitItem()
            if (emission1 is Resource.Loading) {
                assertThat("Loading").isEqualTo("Loading")
            }
            val emission2 = awaitItem()
            if (emission2 is Resource.Success) {
                val id = emission2.data?.id
                assertThat(id).isNotEqualTo(0)
            }
            cancelAndConsumeRemainingEvents()
        }
    }
}