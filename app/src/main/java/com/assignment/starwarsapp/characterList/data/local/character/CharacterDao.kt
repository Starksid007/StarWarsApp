package com.assignment.starwarsapp.characterList.data.local.character

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

/*
Author: Siddharth Kushwaha
Date: 11 Jan 2023
*/

@Dao
interface CharacterDao {

    @Upsert
    suspend fun upsertCharactersList(characterList: List<CharacterEntity>)

    @Query("SELECT * FROM CharacterEntity")
    suspend fun getCharactersList(): List<CharacterEntity>

}