package com.matteopasotti.whatmovie.compose

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.matteopasotti.whatmovie.R

val fonts = FontFamily(
    Font(R.font.product_sans_bold, weight = FontWeight.Bold),
    Font(R.font.product_sans_bold_italic, weight = FontWeight.SemiBold),
    Font(R.font.product_sans_italic, weight = FontWeight.Light)
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    h1 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    body2 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    )
)