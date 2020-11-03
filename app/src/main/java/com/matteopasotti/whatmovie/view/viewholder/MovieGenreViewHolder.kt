package com.matteopasotti.whatmovie.view.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.matteopasotti.whatmovie.R
import com.matteopasotti.whatmovie.model.Genre

class MovieGenreViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private var genreLabel: TextView = itemView.findViewById(R.id.genre_label)

    fun bindData(genre: Genre) {
        genreLabel.text = genre.name
    }
}