package com.assignment.starwarsapp.moviesList.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MovieDao {

    @Upsert
    suspend fun upsertMovie(movie: MovieEntity)

    @Query("SELECT * FROM MovieEntity WHERE movie_url = :movieUrl")
    suspend fun getMovie(movieUrl: String): MovieEntity?
}