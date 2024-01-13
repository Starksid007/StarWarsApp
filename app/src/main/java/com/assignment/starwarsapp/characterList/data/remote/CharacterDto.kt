package com.assignment.starwarsapp.characterList.data.remote

/*
Author: Siddharth Kushwaha
Date: 11 Jan 2023
*/

data class CharacterDto (
    var name: String,
    var gender: String,
    var height: String,
    var birth_year: String,
    var films: List<String>
)