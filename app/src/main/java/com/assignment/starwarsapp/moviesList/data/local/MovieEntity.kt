package com.assignment.starwarsapp.moviesList.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity (
    var title: String,
    var director: String,
    var producer: String,
    var release_date: String,
    @PrimaryKey
    var movie_url: String
)