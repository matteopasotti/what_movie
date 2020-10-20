package com.matteopasotti.whatmovie.util

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.matteopasotti.whatmovie.R
import com.matteopasotti.whatmovie.view.adapter.BaseAdapter

class CustomLabelListView@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val labelTxt: TextView
    private val rv: RecyclerView
    private var adapter: BaseAdapter? = null

    init {
        val view = View.inflate(context, R.layout.custom_label_list_view, this)
        labelTxt = view.findViewById(R.id.label)
        rv = view.findViewById(R.id.rv)
        rv.setHasFixedSize(true)
        val manager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv.layoutManager = manager
    }

    fun setCustomLabelListView(labelText: String, adapter: BaseAdapter) {
        this.labelTxt.text = labelText
        rv.adapter = adapter
        this.adapter = adapter
    }

    fun addItems(items: List<Any?>) {
        this.adapter?.also {
            it.addItems(items)
        }
    }
}