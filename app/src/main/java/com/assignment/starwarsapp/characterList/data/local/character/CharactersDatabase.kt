package com.assignment.starwarsapp.characterList.data.local.character

import androidx.room.Database
import androidx.room.RoomDatabase
import dagger.Provides

/*
Author: Siddharth Kushwaha
Date: 11 Jan 2023
*/

@Database(
    entities = [CharacterEntity::class],
    version = 2
)
abstract class CharactersDatabase: RoomDatabase() {

    abstract val characterDao: CharacterDao
}