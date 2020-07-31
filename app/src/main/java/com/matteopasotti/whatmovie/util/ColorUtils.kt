package com.matteopasotti.whatmovie.util

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.GradientDrawable
import android.os.Build
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.palette.graphics.Palette
import com.matteopasotti.whatmovie.R

class ColorUtils {
    companion object {

        interface GradientBackgroundCallkback {
            fun backgroundReady(bg: GradientDrawable)
        }

        fun getGradientColor(res: Resources, bitmap: Bitmap, callkback: GradientBackgroundCallkback) {
            var black: Int
            var lightVibrant: Int
            var colors: IntArray


            Palette.from(bitmap).generate {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    it?.let {
                        black = res.getColor(R.color.black, null)
                        lightVibrant = it.getLightVibrantColor(black)
                        colors = intArrayOf(black, black, lightVibrant)
                        callkback.backgroundReady(GradientDrawable(GradientDrawable.Orientation.BR_TL, colors))
                    }

                } else {
                    it?.let {
                        black = res.getColor(R.color.black)
                        lightVibrant = it.getLightVibrantColor(black)
                        colors = intArrayOf(black, black, lightVibrant)
                        callkback.backgroundReady(GradientDrawable(GradientDrawable.Orientation.BR_TL, colors))
                    }
                }

            }

        }
    }
}