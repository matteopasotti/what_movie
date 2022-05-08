package com.matteopasotti.core

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberImagePainter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MovieImage(
    modifier: Modifier,
    imageUrl: String,
    contentDescription: String? = null,
    @DrawableRes placeholder: Int? = null,
    @DrawableRes errorImage: Int? = null
) {
    Image(
        painter = rememberImagePainter(
            data = imageUrl,
            builder = {
                crossfade(true)
                errorImage?.let { error(it) }
                placeholder?.let { placeholder(it) }

            }),
        modifier = modifier.fillMaxWidth(),
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop
    )
}

@Composable
fun CircleImage(
    imageUrl: String,
    contentDescription: String? = null,
    @DrawableRes placeholder: Int? = null
) {
    Card(
        // test tag is use to add tag to our image. 
        modifier = Modifier
            .height(60.dp)
            .width(60.dp)
            .testTag(tag = "circle"),
        shape = CircleShape,
        elevation = 12.dp
    ) {
        // below line we are creating a new image.
        Image(painter =
        rememberImagePainter(
            data = imageUrl,
            builder = {
                crossfade(true)
                placeholder?.let { placeholder(it) }
            }
        ),
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize())
    }
}

@Composable
fun BlurryBackground(modifier: Modifier) {
    //Box(modifier = modifier.background())
}

@Composable
@Preview
fun ShowCircleImage() {
    CircleImage(imageUrl = "", "", R.drawable.venom_cover)
}