package com.assignment.starwarsapp.moviesList.data.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.assignment.starwarsapp.util.Resource
import com.assignment.starwarsapp.moviesList.data.local.MovieDatabase
import com.assignment.starwarsapp.moviesList.data.local.MovieEntity
import com.assignment.starwarsapp.moviesList.data.remote.MovieApi
import com.assignment.starwarsapp.moviesList.domain.model.Movie
import com.assignment.starwarsapp.moviesList.domain.model.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieDatabase: MovieDatabase,
    private val movieApi: MovieApi
) : MovieRepository {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getMovie(
        movieUrl: String,
        forceFetchFromRemote: Boolean
    ): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.Loading(true))
            val localMovie = movieDatabase.movieDao.getMovie(movieUrl)
            val shouldLoadLocalMovie = localMovie != null && forceFetchFromRemote
            if (shouldLoadLocalMovie) {
                emit(
                    Resource.Success(
                        data = Movie(
                            localMovie!!.title,
                            localMovie.director,
                            localMovie.producer,
                            localMovie.release_date
                        )
                    )
                )

                emit(Resource.Loading(false))
                return@flow
            }

            val movieFromApi = try {
                movieApi.getMovie(movieUrl)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error Loading Movie"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error Loading Movie"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error Loading Movie"))
                return@flow
            }

            val movieEntity = movieFromApi.let {
                MovieEntity(
                    it.title,
                    it.director,
                    it.producer,
                    it.release_date,
                    movieUrl
                )
            }

            movieDatabase.movieDao.upsertMovie(movieEntity)

            emit(
                Resource.Success(
                Movie(
                    movieEntity.title,
                    movieEntity.director,
                    movieEntity.producer,
                    movieEntity.release_date
                )
            ))
            emit(Resource.Loading(false))
        }
    }
}