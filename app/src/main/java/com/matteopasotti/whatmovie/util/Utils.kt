package com.matteopasotti.whatmovie.util

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.Log
import androidx.core.widget.NestedScrollView
import androidx.palette.graphics.Palette
import com.matteopasotti.whatmovie.R


object Utils {

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
                    callkback.backgroundReady(
                        GradientDrawable(
                            GradientDrawable.Orientation.BR_TL,
                            colors
                        )
                    )
                }

            } else {
                it?.let {
                    black = res.getColor(R.color.black)
                    lightVibrant = it.getLightVibrantColor(black)
                    colors = intArrayOf(black, black, lightVibrant)
                    callkback.backgroundReady(
                        GradientDrawable(
                            GradientDrawable.Orientation.BR_TL,
                            colors
                        )
                    )
                }
            }

        }

    }

    class InfiniteScrollListener(
        val func: () -> Unit,
        val layoutManager: androidx.recyclerview.widget.GridLayoutManager
    ) :
        androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
        private var previousTotal = 0
        private var loading = true
        private var visibleThreshold = 3
        private var firstVisibleItem = 0
        private var visibleItemCount = 0
        private var totalItemCount = 0

        override fun onScrolled(
            recyclerView: androidx.recyclerview.widget.RecyclerView,
            dx: Int,
            dy: Int
        ) {
            super.onScrolled(recyclerView, dx, dy)


            if (dy > 0) {
                totalItemCount = layoutManager.itemCount
                if (totalItemCount != previousTotal) {
                    loading = false
                }
                if (!loading && layoutManager.findLastVisibleItemPosition() >= totalItemCount - 1) {
                    previousTotal = totalItemCount
                    func()
                    loading = true
                }
            }
        }

    }

    class NestedInfiniteScrollListener(val func: () -> Unit) : NestedScrollView.OnScrollChangeListener {
        override fun onScrollChange(v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
            if (v?.getChildAt(v.childCount - 1) != null) {
                if (scrollY >= v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight && scrollY > oldScrollY) {
                    //code to fetch more data for endless scrolling
                    func()
                }
            }
        }

    }
}