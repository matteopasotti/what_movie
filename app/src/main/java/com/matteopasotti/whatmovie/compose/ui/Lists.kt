package com.matteopasotti.whatmovie.compose.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.matteopasotti.core.MovieCard
import com.matteopasotti.whatmovie.compose.Typography
import com.matteopasotti.whatmovie.model.MovieDomainModel

@Composable
fun MoviesList(
    label: String,
    movies: List<MovieDomainModel>,
    onMovieClicked: (MovieDomainModel) -> Unit
) {
    Column {
        Text(text = label, color = Color.White, style = Typography.body1)
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .fillMaxWidth()
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(movies) {
                MovieCard(imageUrl = it.poster_path) { onMovieClicked(it) }
            }
        }
    }

}