package com.matteopasotti.core

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@ExperimentalPagerApi
@Composable
fun AutoSlider(images: List<String>, onItemClicked: () -> Unit) {
    val pagerState = rememberPagerState()

    HorizontalPager(count = images.size, state = pagerState) { page ->
        MovieImage(modifier = Modifier
            .height(300.dp)
            .fillMaxWidth(), imageUrl = images[page])
    }

}