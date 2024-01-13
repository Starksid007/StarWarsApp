package com.assignment.starwarsapp.core.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.assignment.starwarsapp.characterList.presentation.CharactersListState
import com.assignment.starwarsapp.characterList.presentation.CharactersListViewModel
import com.assignment.starwarsapp.core.presentation.components.CharacterItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {

    val charactersListViewModel = hiltViewModel<CharactersListViewModel>()
    val charactersState = charactersListViewModel.charactersListState.collectAsState().value
    var searchText by remember { mutableStateOf("") }
    var isBottomSheetVisible by rememberSaveable { mutableStateOf(false) }
    var selectedSortOption by remember { mutableStateOf("") }
    var isClearSortVisible by remember { mutableStateOf(false) }
    var clearSortOption by remember { mutableStateOf("") }


    val onSortIconClick: () -> Unit = {
        isBottomSheetVisible = !isBottomSheetVisible
    }

    val onClearSortClick: () -> Unit = {
        isClearSortVisible = !isClearSortVisible
    }


    if (isBottomSheetVisible) {
        BottomSheetScaffold(
            modifier = Modifier.clickable {
                isBottomSheetVisible = false
            },
            sheetContent = {
                Box {
                    // Sorting Options
                    LazyColumn {
                        item {
                            Text(
                                text = "Sort By",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                        items(listOf("Name", "Gender", "Birth Year")) { option ->
                            BottomSheetItem(
                                option = option,
                                onOptionSelected = {
                                    isBottomSheetVisible = false
                                    selectedSortOption = option
                                    isClearSortVisible = true
                                    clearSortOption = option
                                    charactersListViewModel.sortCharacters(option)
                                }
                            )
                        }
                    }
                }

            },
            scaffoldState = rememberBottomSheetScaffoldState(),
            sheetPeekHeight = 300.dp,
            sheetShape = MaterialTheme.shapes.medium,
            content = {
                HomeContent(
                    navController = navController,
                    searchText = searchText,
                    charactersState = charactersState,
                    charactersListViewModel = charactersListViewModel,
                    onSortIconClick =  onSortIconClick,
                    isClearSortVisible = isClearSortVisible,
                    onClearSortClick = onClearSortClick,
                    clearSortOption = clearSortOption
                ) {
                    searchText = it
                }
            }
        )
    } else {
        HomeContent(
            navController = navController,
            searchText = searchText,
            charactersState = charactersState,
            charactersListViewModel = charactersListViewModel,
            onSortIconClick = onSortIconClick,
            isClearSortVisible = isClearSortVisible,
            onClearSortClick = onClearSortClick,
            clearSortOption = clearSortOption
        ) {
            searchText = it
        }
    }
}


@Composable
fun HomeContent(
    navController: NavHostController,
    searchText: String,
    charactersState: CharactersListState,
    charactersListViewModel: CharactersListViewModel,
    onSortIconClick: () -> Unit,
    isClearSortVisible: Boolean,
    onClearSortClick: () -> Unit,
    clearSortOption: String,
    onSearchTextChanged: (String) -> Unit
) {
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
                text = "STAR WARS - Characters",
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier
                    .padding(start = 12.dp, top = 8.dp)
            )
        }

        OutlinedTextField(
            value = searchText,
            onValueChange = { newText ->
                onSearchTextChanged(newText)
                charactersListViewModel.searchCharacters(newText)
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondaryContainer
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.clickable {
                        onSearchTextChanged("")
                        charactersListViewModel.searchCharacters(searchText)
                    }
                )
            },
            placeholder = { Text("Search characters by name", color = Color.Gray) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.1f))
                .shadow(shape = RoundedCornerShape(3.dp), elevation = 3.dp)

        )

        Icon(
            imageVector = Icons.Default.Sort,
            contentDescription = "sort",
            tint = MaterialTheme.colorScheme.secondaryContainer,
            modifier = Modifier
                .padding(start = 16.dp)
                .size(30.dp)
                .clickable {
                    onSortIconClick()
                }
        )

        if(isClearSortVisible) {
            Card(
                modifier = Modifier
                    .clickable {
                        onClearSortClick()
                        charactersListViewModel.sortCharacters("")
                    }
                    .padding(12.dp)
                    .background(MaterialTheme.colorScheme.contentColorFor(Color(0)))
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "$clearSortOption",
                        color = MaterialTheme.colorScheme.secondary,
                    )
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear Sorting",
                        Modifier
                            .height(16.dp)
                            .width(16.dp)
                    )
                }
            }
        }

        if (charactersState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp)
            ) {
                items(charactersState.charactersList.size) { index ->
                    CharacterItem(
                        character = charactersState.charactersList[index],
                        navHostController = navController
                    )
                }
            }
        }
    }
}


@Composable
fun BottomSheetItem(option: String, onOptionSelected: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onOptionSelected)
            .padding(16.dp)
    ) {
        Text(text = option)
    }
}

