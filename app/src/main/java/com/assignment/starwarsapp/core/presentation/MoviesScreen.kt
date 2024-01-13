package com.assignment.starwarsapp.core.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.assignment.starwarsapp.core.presentation.components.MovieItem
import com.assignment.starwarsapp.moviesList.presentation.MoviesViewModel

/*
Author: Siddharth Kushwaha
Date: 11 Jan 2023
*/

@Composable
fun MoviesScreen () {

    val movieViewModel = hiltViewModel<MoviesViewModel>()
    val moviesListState = movieViewModel.moviesState.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
                .align(Alignment.Start)
                .background(MaterialTheme.colorScheme.inverseOnSurface),
        ) {
            Text(
                text = "${moviesListState.characterName}'s Movies",
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier.padding(start = 12.dp, top = 8.dp)
            )
        }

        if (moviesListState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp)
            ) {
                items(moviesListState.movies.size) { index ->
                    MovieItem(
                        movie = moviesListState.movies[index],
                    )
                }
            }
        }
    }

}