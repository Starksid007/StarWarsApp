package com.assignment.starwarsapp.di

import com.assignment.starwarsapp.characterList.data.repository.CharactersListRepositoryImpl
import com.assignment.starwarsapp.characterList.domain.model.CharactersListRepository
import com.assignment.starwarsapp.moviesList.data.repository.MovieRepositoryImpl
import com.assignment.starwarsapp.moviesList.domain.model.MovieRepository
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
    abstract fun bindCharactersListRepository(
        charactersListRepositoryImpl: CharactersListRepositoryImpl
    ): CharactersListRepository

    @Binds
    @Singleton
    abstract fun bindMovieRepository(
        movieRepositoryImpl: MovieRepositoryImpl
    ): MovieRepository
}