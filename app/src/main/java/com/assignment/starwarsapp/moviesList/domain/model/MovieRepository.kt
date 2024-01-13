package com.assignment.starwarsapp.moviesList.domain.model

import com.assignment.starwarsapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getMovie (
        movieUrl: String,
        forceFetchFromRemote: Boolean
    ): Flow<Resource<Movie>>
}