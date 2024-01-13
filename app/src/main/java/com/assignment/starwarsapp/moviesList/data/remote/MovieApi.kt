package com.assignment.starwarsapp.moviesList.data.remote

import retrofit2.http.GET
import retrofit2.http.Url

interface MovieApi {
    @GET
    suspend fun getMovie(@Url url: String): MovieDto

    companion object {
        const val BASE_URL = "https://swapi.dev/api/"
    }
}

