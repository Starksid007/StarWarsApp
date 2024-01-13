package com.assignment.starwarsapp.characterList.data.local.character

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

/*
Author: Siddharth Kushwaha
Date: 11 Jan 2023
*/

@Entity
data class CharacterEntity (
    var name: String,
    var gender: String,
    var height: String,
    var birth_year: String,
    var films: String,
    @PrimaryKey
    var id: String = UUID.randomUUID().toString()
)