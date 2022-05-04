package com.matteopasotti.whatmovie.view.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.matteopasotti.whatmovie.R
import com.matteopasotti.whatmovie.model.CarouselEntity

class CarouselHolder<T : CarouselEntity>(
    itemView: View,
    private val clicked: (T) -> Unit
) : RecyclerView.ViewHolder(itemView) {


    private val iv = itemView.findViewById<ImageView>(R.id.carousel_image)

    private val title = itemView.findViewById<TextView>(R.id.carousel_title)

    fun bind(data: T) {
        Glide.with(itemView)
            .load(data.imgUrl)
            .into(iv)

        title.text = data.carouselItemTitle

        itemView.setOnClickListener {
            clicked(data)
        }
    }
}