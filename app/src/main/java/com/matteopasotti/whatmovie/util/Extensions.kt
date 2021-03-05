package com.matteopasotti.whatmovie.util

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Context.dpToPx(dp: Float): Float {
    return dp * getPixelScaleFactor(this)
}

private fun getPixelScaleFactor(context: Context): Float {
    val displayMetrics = context.resources.displayMetrics
    return displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT
}