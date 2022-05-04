package com.matteopasotti.core

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val defaultMovieCardModifier = Modifier
    .width(160.dp)
    .height(210.dp)
    .clip(RoundedCornerShape(8.dp))

@Composable
fun MovieCard(
    modifier: Modifier = defaultMovieCardModifier,
    imageUrl: String,
    onItemClicked: () -> Unit
) {
    Box(
        modifier = modifier
            .clickable { onItemClicked() },
        contentAlignment = Alignment.Center
    ) {
        MovieImage(
            modifier = Modifier
                .fillMaxSize(),
            imageUrl = imageUrl,
            placeholder = R.drawable.venom_cover
        )
    }

}

@Composable
@Preview
fun ShowMovieCardPreview() {
    MovieCard(
        modifier = defaultMovieCardModifier, imageUrl = ""
    ) {

    }
}