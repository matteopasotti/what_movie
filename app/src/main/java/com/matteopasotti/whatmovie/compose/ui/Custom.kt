package com.matteopasotti.whatmovie.compose.ui

import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.matteopasotti.whatmovie.compose.*
import com.matteopasotti.whatmovie.model.Genre
import com.matteopasotti.whatmovie.model.MovieDetailDomainModel
import com.matteopasotti.whatmovie.R

@Composable
fun AboutLayout(movieDetail: MovieDetailDomainModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = MidBlue)
            .padding(16.dp)

    ) {

        Text(text = "About Movie", color = Color.White, style = Typography.body1)
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .fillMaxWidth()
        )
        TextRow(title = "Original Title", value = movieDetail.originalTitle)
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .fillMaxWidth()
        )
        TextRow(title = "Type", value = movieDetail.type)
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .fillMaxWidth()
        )
        TextRow(title = "Language", value = movieDetail.language)
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .fillMaxWidth()
        )
        TextRow(title = "Premiere", value = movieDetail.releaseDate)
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .fillMaxWidth()
        )
        TextRow(title = "Storyline", value = movieDetail.overview)

    }
}

@Composable
fun TextRow(title: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        Text(text = "$title:", color = Color.White, style = Typography.body2)
        Spacer(
            modifier = Modifier
                .width(8.dp)
        )
        Text(text = value, color = Color.LightGray, style = Typography.body2)

    }
}

@Composable
fun RatingView(rating: Float, modifier: Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(10.dp))
            .background(brush = Brush.horizontalGradient(colors = listOf(Pink, Purple200, Violet)))
    ) {
        Text(
            text = rating.toString(),
            color = Color.White,
            style = Typography.h1,
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 12.dp)
        )
    }


}

@Composable
fun IconText(text: String, modifier: Modifier? = null, @DrawableRes drawable: Int, @ColorInt textColor: Color) {
    Row(modifier = modifier ?: Modifier.wrapContentSize(), verticalAlignment = Alignment.CenterVertically) {
        Image(painter = painterResource(id = drawable), contentDescription = null)
        Text(
            text = text,
            color = textColor,
            style = Typography.body2,
            modifier = Modifier.padding(start = 3.dp)
        )
    }

}

@Composable
@Preview
fun ShowRatingView() {
    MaterialTheme {
        RatingView(
            rating = 8f,
            Modifier
                .wrapContentSize()
                .clip(RoundedCornerShape(10.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Pink,
                            Purple200,
                            Violet
                        )
                    )
                )
        )
    }
}

@Composable
@Preview
fun ShowAboutLayout() {
    MaterialTheme {
        AboutLayout(
            movieDetail = MovieDetailDomainModel(
                type = "Action",
                originalTitle = "Iron Man",
                duration = "2h",
                overview = "overview",
                language = "EN",
                vote_average = 8f,
                genres = listOf(Genre(1, "Action")),
                productionCountries = "USA",
                releaseDate = "22/1/2018",
                videos = emptyList()
            )
        )
    }
}

@Composable
@Preview
fun ShowTextRow() {
    MaterialTheme {
        TextRow(title = "Original title", value = "Iron Man")
    }
}

@Composable
@Preview
fun ShowTextIcon() {
    MaterialTheme {
        IconText(text = "1h 30m", drawable = R.drawable.ic_clock, textColor = Pink)
    }
}