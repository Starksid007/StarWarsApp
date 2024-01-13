package com.assignment.starwarsapp.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.assignment.starwarsapp.R
import com.assignment.starwarsapp.characterList.domain.model.Character
import com.assignment.starwarsapp.util.Screen

@Composable
fun CharacterItem(
    character: Character,
    navHostController: NavHostController
) {

    val defaultColor = MaterialTheme.colorScheme.secondaryContainer
    var dominantColor by remember {
        mutableStateOf(defaultColor)
    }

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .width(200.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.secondaryContainer,
                        dominantColor
                    )
                )
            )
            .clickable {
                navHostController.navigate(
                    Screen.Movies.rout + "?films=" + character.films + "?characterName=" + character.name
                )
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
                .height(100.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painterResource(R.drawable.character),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            modifier = Modifier.padding(start = 20.dp, end = 6.dp),
            text = character.name,
            color = Color.White,
            fontSize = 18.sp,
            maxLines = 1
        )

        Text(
            modifier = Modifier.padding(start = 20.dp, end = 6.dp),
            text = character.gender,
            color = Color.LightGray,
            fontSize = 16.sp,
            maxLines = 1
        )

        Text(
            modifier = Modifier.padding(start = 20.dp, end = 6.dp, bottom = 10.dp),
            text = character.birthYear,
            color = Color.LightGray,
            fontSize = 14.sp,
            maxLines = 1
        )

    }
}