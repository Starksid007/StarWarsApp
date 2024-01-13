package com.assignment.starwarsapp.characterList.presentation

import com.assignment.starwarsapp.characterList.domain.model.Character

/*
Author: Siddharth Kushwaha
Date: 11 Jan 2023
*/

data class CharactersListState(
    var isLoading: Boolean = false,
    var charactersList: List<Character> = emptyList()
)