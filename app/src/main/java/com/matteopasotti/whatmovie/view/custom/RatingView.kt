package com.matteopasotti.whatmovie.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.matteopasotti.whatmovie.R

class RatingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val scoreTxt: TextView

    init {
        val view = View.inflate(context, R.layout.rating_layout, this)
        scoreTxt = view.findViewById(R.id.rating_value)
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.RatingView, defStyleAttr, 0)
            val score: Float = a.getFloat(R.styleable.RatingView_ratingScore, 0F)
            scoreTxt.text = score.toString()
            a.recycle()
        }
    }

    fun setRatingScore(score: Float) {
        scoreTxt.text = score.toString()
        invalidate()
        requestLayout()
    }
}