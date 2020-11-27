package com.matteopasotti.whatmovie.util

import androidx.core.widget.NestedScrollView


object Utils {

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