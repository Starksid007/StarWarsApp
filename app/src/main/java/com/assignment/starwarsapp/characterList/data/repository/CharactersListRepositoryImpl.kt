package com.assignment.starwarsapp.characterList.data.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.assignment.starwarsapp.characterList.data.local.character.CharacterEntity
import com.assignment.starwarsapp.characterList.data.local.character.CharactersDatabase
import com.assignment.starwarsapp.characterList.data.remote.CharacterApi
import com.assignment.starwarsapp.characterList.domain.model.Character
import com.assignment.starwarsapp.characterList.domain.model.CharactersListRepository
import com.assignment.starwarsapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

/*
Author: Siddharth Kushwaha
Date: 11 Jan 2023
*/

class CharactersListRepositoryImpl @Inject constructor (
    private val charactersDatabase: CharactersDatabase,
    private val characterApi: CharacterApi
): CharactersListRepository {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getCharactersList(
        forceFetchFromRemote: Boolean
    ): Flow<Resource<List<Character>>> {
        return flow {
            emit(Resource.Loading(true))
            val localCharactersList = charactersDatabase.characterDao.getCharactersList()
            val shouldLoadLocalCharacters = localCharactersList.isNotEmpty() && forceFetchFromRemote
            if(shouldLoadLocalCharacters) {
                emit(
                    Resource.Success(
                    data = localCharactersList.map {
                        Character(
                            it.name,
                            it.gender,
                            it.height,
                            it.birth_year,
                            it.films.split(",")
                        )
                    }
                ))

                emit(Resource.Loading(false))
                return@flow
            }

            val charactersListFromApi = try {
                characterApi.getCharactersList()
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error Loading Characters"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error Loading Characters"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error Loading Characters"))
                 return@flow
            }

            val charactersEntities = charactersListFromApi.results.let { list->
                list.map {
                    CharacterEntity(it.name, it.gender, it.height, it.birth_year, it.films.joinToString(","))
                }
            }

            charactersDatabase.characterDao.upsertCharactersList(charactersEntities)

            emit(
                Resource.Success(
                charactersEntities.map {
                    Character(it.name, it.gender, it.height, it.birth_year, it.films.split(","))
                }
            ))
            emit(Resource.Loading(false))
        }
    }
}