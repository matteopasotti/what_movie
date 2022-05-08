package com.matteopasotti.whatmovie.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matteopasotti.whatmovie.R
import com.matteopasotti.whatmovie.model.CarouselEntity
import com.matteopasotti.whatmovie.view.viewholder.CarouselHolder

class CarouselAdapter<T : CarouselEntity>(
    private val clicked: (T) -> Unit,
    private val items: MutableList<T> = mutableListOf<T>()
) : RecyclerView.Adapter<CarouselHolder<T>>() {

    val actualItemCount
        get() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselHolder<T> =
        LayoutInflater.from(parent.context).inflate(R.layout.carousel_image_item, parent, false)
            .let {
                CarouselHolder(it, clicked)
            }

    override fun getItemCount(): Int = if (items.isEmpty()) 0 else Integer.MAX_VALUE

    override fun onBindViewHolder(holder: CarouselHolder<T>, position: Int) {
        items[position % actualItemCount].let(holder::bind)
    }

    fun setItems(value: List<T>) {
        items.clear()
        items.addAll(value)
        notifyDataSetChanged()
    }
}