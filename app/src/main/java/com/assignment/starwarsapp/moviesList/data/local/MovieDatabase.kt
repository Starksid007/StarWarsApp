package com.assignment.starwarsapp.moviesList.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MovieEntity::class],
    version = 2
)
abstract class MovieDatabase: RoomDatabase() {
    abstract val movieDao: MovieDao
}