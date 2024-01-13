package com.assignment.starwarsapp.moviesList.domain.model

import com.google.gson.annotations.SerializedName

data class Movie (
    var title: String,
    var director: String,
    var producer: String,
    @SerializedName("release_date")
    var releaseDate: String
)