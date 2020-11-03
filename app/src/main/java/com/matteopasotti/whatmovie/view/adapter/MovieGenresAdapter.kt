package com.matteopasotti.whatmovie.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matteopasotti.whatmovie.R
import com.matteopasotti.whatmovie.model.Genre
import com.matteopasotti.whatmovie.util.inflate
import com.matteopasotti.whatmovie.view.viewholder.MovieGenreViewHolder

class MovieGenresAdapter : RecyclerView.Adapter<MovieGenreViewHolder>() {

    private var genres: MutableList<Genre> = mutableListOf()

    fun updateItems(genres: List<Genre>) {
        this.genres.addAll(genres)
        notifyItemInserted(genres.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieGenreViewHolder {
        val v = parent.inflate(R.layout.genre_item)
        return MovieGenreViewHolder(v)
    }

    override fun getItemCount(): Int {
        return genres.size
    }

    override fun onBindViewHolder(holder: MovieGenreViewHolder, position: Int) {
        holder.bindData(genres[position])
    }
}