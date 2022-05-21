package com.anupdey.app.bongotalkies.di

import com.anupdey.app.bongotalkies.data.repository.MovieRepository
import com.anupdey.app.bongotalkies.data.repository.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(
        repository: MovieRepositoryImpl
    ): MovieRepository
}