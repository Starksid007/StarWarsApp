package com.assignment.starwarsapp.util

/*
Author: Siddharth Kushwaha
Date: 11 Jan 2023
*/

sealed class Screen(val rout: String) {
    object Home : Screen("main")
    object Movies : Screen("movies")
}