package com.matteopasotti.whatmovie.view.viewholder

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(private val view : View): RecyclerView.ViewHolder(view), View.OnClickListener {

    init {
        view.setOnClickListener(this)
    }

    @Throws(Exception::class)
    abstract fun bindData(data: Any?)

    protected fun view(): View {
        return view
    }

    protected fun context(): Context {
        return view.context
    }
}