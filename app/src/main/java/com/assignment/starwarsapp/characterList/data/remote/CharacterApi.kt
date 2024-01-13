package com.assignment.starwarsapp.characterList.data.remote

import retrofit2.http.GET

/*
Author: Siddharth Kushwaha
Date: 11 Jan 2023
*/

interface CharacterApi {

    @GET("people/")
    suspend fun getCharactersList(): ApiResponse<List<CharacterDto>>

    companion object {
        const val BASE_URL = "https://swapi.dev/api/"
    }
}