package com.matteopasotti.whatmovie.view.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.matteopasotti.whatmovie.model.CarouselEntity
import kotlinx.android.synthetic.main.carousel_image_item.view.*

class CarouselHolder<T : CarouselEntity>(
    itemView: View,
    private val clicked: (T) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val iv by lazy {
        itemView.carousel_image
    }

    fun bind(data: T) {
        Glide.with(itemView)
            .load(data.imgUrl)
            .into(iv)

        itemView.setOnClickListener {
            clicked(data)
        }
    }
}