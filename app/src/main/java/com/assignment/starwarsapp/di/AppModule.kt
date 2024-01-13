package com.assignment.starwarsapp.di

import android.app.Application
import androidx.room.Room
import com.assignment.starwarsapp.characterList.data.local.character.CharactersDatabase
import com.assignment.starwarsapp.characterList.data.remote.CharacterApi
import com.assignment.starwarsapp.moviesList.data.local.MovieDatabase
import com.assignment.starwarsapp.moviesList.data.remote.MovieApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    @Singleton
    @Provides
    fun providesCharactersApi(): CharacterApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(CharacterApi.BASE_URL)
            .client(client)
            .build()
            .create(CharacterApi::class.java)
    }

    @Singleton
    @Provides
    fun providesMovieApi(): MovieApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(MovieApi.BASE_URL)
            .client(client)
            .build()
            .create(MovieApi::class.java)
    }

    @Provides
    @Singleton
    fun providesCharactersDatabase(app: Application): CharactersDatabase {
        return Room.databaseBuilder(
            app,
            CharactersDatabase::class.java,
            "charactersdb.db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesMovieDatabase(app: Application): MovieDatabase {
        return Room.databaseBuilder(
            app,
            MovieDatabase::class.java,
            "moviesdb.db"
        ).build()
    }
}