package com.matteopasotti.whatmovie.view.custom

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.matteopasotti.whatmovie.R

class CustomFabButton@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr)  {

    private var mFabIcon: Drawable

    init {

        View.inflate(context, R.layout.custom_fab_button, this)

        val imageView: ImageView = findViewById(R.id.fab_icon)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomFabButton,
            0, 0).apply {

            try {
                mFabIcon = getDrawable(R.styleable.CustomFabButton_fabIcon)
                imageView.setImageDrawable(mFabIcon)

            } finally {
                recycle()
            }
        }
    }

    fun setFabIcon(fabIcon: Drawable) {
        mFabIcon = fabIcon
        invalidate()
        requestLayout()
    }
}