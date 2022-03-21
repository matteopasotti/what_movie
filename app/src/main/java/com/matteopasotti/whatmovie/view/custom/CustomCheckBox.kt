package com.matteopasotti.whatmovie.view.custom

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.CheckBox
import android.widget.FrameLayout
import com.matteopasotti.whatmovie.R

class CustomCheckBox@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var mCheckboxBackground: Drawable

    init {
        View.inflate(context, R.layout.custom_checkbox, this)

        val checkbox: CheckBox = findViewById(R.id.custom_checkbox)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomCheckBox,
            0, 0).apply {

            try {
                mCheckboxBackground = getDrawable(R.styleable.CustomCheckBox_checkboxBackground)!!
                checkbox.background = mCheckboxBackground

            } finally {
                recycle()
            }
        }
    }
}