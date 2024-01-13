package com.assignment.starwarsapp.characterList.domain.model

import com.google.gson.annotations.SerializedName

/*
Author: Siddharth Kushwaha
Date: 11 Jan 2023
*/

data class Character (
    var name: String,
    var gender: String,
    var height: String,
    @SerializedName("birth_year")
    var birthYear: String,
    var films: List<String>
)