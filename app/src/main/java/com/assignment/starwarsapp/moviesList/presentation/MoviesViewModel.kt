package com.assignment.starwarsapp.moviesList.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.starwarsapp.util.Resource
import com.assignment.starwarsapp.moviesList.domain.model.Movie
import com.assignment.starwarsapp.moviesList.domain.model.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Arrays
import javax.inject.Inject

/*
Author: Siddharth Kushwaha
Date: 11 Jan 2023
*/

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val filmsUrls = savedStateHandle.get<Array<String>>("films")!![0].replace("[", "").replace("]", "").split(",")
    private val characterName = savedStateHandle.get<String>("characterName")

    private var _moviesState = MutableStateFlow(MoviesListState())
    val moviesState = _moviesState.asStateFlow()

    init {
        getAllMovies(true)
    }

    private fun getAllMovies(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {

            _moviesState.update {
                it.copy(isLoading = true)
            }

            _moviesState.update {
                it.copy(characterName = characterName!!)
            }

            val moviesList = mutableListOf<Movie>() // Assuming Movie is the type of your movies
            for (filmUrl in filmsUrls) {
                println(filmsUrls)
                println(filmUrl)
                movieRepository.getMovie(filmUrl, forceFetchFromRemote).collect { res ->
                    when (res) {
                        is Resource.Error -> {
                            _moviesState.update {
                                it.copy(isLoading = false)
                            }
                        }

                        is Resource.Success -> {
                            res.data?.let { movie ->
                                moviesList.add(movie)
                            }
                        }

                        is Resource.Loading -> {
                            _moviesState.update {
                                it.copy(isLoading = res.isLoading)
                            }
                        }
                    }
                }
            }

            _moviesState.update {
                it.copy(
                    isLoading = false,
                    movies = moviesState.value.movies + moviesList.toList()
                )
            }
        }
    }
}
