package com.assignment.starwarsapp.characterList.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.starwarsapp.characterList.domain.model.Character
import com.assignment.starwarsapp.characterList.domain.model.CharactersListRepository
import com.assignment.starwarsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
Author: Siddharth Kushwaha
Date: 11 Jan 2023
*/

@HiltViewModel
class CharactersListViewModel @Inject constructor(
    private val charactersListRepository: CharactersListRepository
) : ViewModel() {

    private var originalCharactersList = emptyList<Character>()

    private var _charactersListState = MutableStateFlow(CharactersListState())
    val charactersListState = _charactersListState.asStateFlow()

    init {
        getCharactersList(true)
    }

    private fun getCharactersList(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _charactersListState.update {
                it.copy(isLoading = true)
            }

            charactersListRepository.getCharactersList(forceFetchFromRemote).collectLatest { res ->
                when (res) {
                    is Resource.Error -> {
                        _charactersListState.update {
                            it.copy(isLoading = false)
                        }

                    }

                    is Resource.Success -> {
                        originalCharactersList = res.data ?: emptyList()
                        res.data.let {
                            _charactersListState.update { listState ->
                                listState.copy(
                                    charactersList = charactersListState.value.charactersList + it!!
                                )
                            }
                        }

                    }

                    is Resource.Loading -> {
                        _charactersListState.update {
                            it.copy(isLoading = res.isLoading)
                        }

                    }
                }
            }
        }
    }

    fun searchCharacters(query: String) {
        viewModelScope.launch {
            _charactersListState.update {
                it.copy(
                    charactersList = if (query.isBlank()) {
                        originalCharactersList
                    } else {
                        originalCharactersList.filter { chr ->
                            chr.name.contains(query, ignoreCase = true)
                        }
                    }
                )
            }
        }
    }

    fun sortCharacters(option: String) {
        viewModelScope.launch {
            when (option) {
                "Name" -> {
                    _charactersListState.getAndUpdate { listState ->
                        listState.copy(
                            charactersList = listState.charactersList.sortedBy { it.name }
                        )
                    }
                }

                "Gender" -> {
                    _charactersListState.getAndUpdate { listState ->
                        listState.copy(
                            charactersList = listState.charactersList.sortedBy { it.gender }
                        )
                    }
                }

                "Birth Year" -> {
                    _charactersListState.getAndUpdate { listState ->
                        listState.copy(
                            charactersList = listState.charactersList.sortedBy { it.birthYear }
                        )
                    }
                }

                else -> _charactersListState.update {
                    it.copy(
                        charactersList = originalCharactersList
                    )
                }
            }
        }
    }
}