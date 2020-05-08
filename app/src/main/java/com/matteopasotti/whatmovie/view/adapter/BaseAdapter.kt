package com.matteopasotti.whatmovie.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.matteopasotti.whatmovie.view.viewholder.BaseViewHolder
import java.util.*

abstract class BaseAdapter : androidx.recyclerview.widget.RecyclerView.Adapter<BaseViewHolder>(){


    val items = ArrayList<Any?>()


    fun <T> addItems(list : List<T>) {
        items.addAll(ArrayList<Any>(list))
    }

    /**
     * Fetch the layout id.
     */
    protected abstract fun layout(item : Any?): Int

    /**
     * Returns a new ViewHolder given a layout and view.
     */
    protected abstract fun viewHolder(@LayoutRes layout: Int, view: View): BaseViewHolder


    override fun onViewAttachedToWindow(holder: BaseViewHolder) {
        super.onViewAttachedToWindow(holder)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, @LayoutRes layout: Int): BaseViewHolder {
        val view = inflateView(viewGroup, layout)
        return viewHolder(layout, view)
    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {
        val data = getItemByPosition(position)

        try {
            viewHolder.bindData(data)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * Gets the data object associated with a position.
     */
    protected fun getItemByPosition(position: Int): Any? {
        return items[position]
    }

    override fun getItemViewType(position: Int): Int {
        var item : Any? = items.get(position)
        return layout(item)
    }


    private fun inflateView(viewGroup: ViewGroup, @LayoutRes viewType: Int): View {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        return layoutInflater.inflate(viewType, viewGroup, false)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}