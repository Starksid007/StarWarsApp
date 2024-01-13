package com.assignment.starwarsapp.moviesList.presentation

import com.assignment.starwarsapp.moviesList.domain.model.Movie

data class MoviesListState (
    var isLoading: Boolean = false,
    var movies: List<Movie> = emptyList(),
    var characterName: String = ""
)