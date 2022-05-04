package com.matteopasotti.core

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberImagePainter
import androidx.compose.foundation.Image

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