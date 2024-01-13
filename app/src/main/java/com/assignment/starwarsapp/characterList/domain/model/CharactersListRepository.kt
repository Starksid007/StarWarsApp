package com.assignment.starwarsapp.characterList.domain.model

import com.assignment.starwarsapp.util.Resource
import kotlinx.coroutines.flow.Flow

/*
Author: Siddharth Kushwaha
Date: 11 Jan 2023
*/


interface CharactersListRepository {

    suspend fun getCharactersList (
        forceFetchFromRemote: Boolean
    ): Flow<Resource<List<Character>>>
}