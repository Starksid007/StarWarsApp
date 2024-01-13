package com.assignment.starwarsapp.characterList.data.remote

/*
Author: Siddharth Kushwaha
Date: 11 Jan 2023
*/

data class ApiResponse<T>(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: T
)