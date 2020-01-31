package com.matteopasotti.whatmovie.view.adapter

import android.view.View
import com.matteopasotti.whatmovie.R
import com.matteopasotti.whatmovie.model.Movie
import com.matteopasotti.whatmovie.view.viewholder.BaseViewHolder
import com.matteopasotti.whatmovie.view.viewholder.MovieViewHolder

class MoviesAdapter(val delegate: MovieViewHolder.Delegate): BaseAdapter() {

    init {
        addItems(ArrayList<Movie>())
    }

    fun updateItems(movies: List<Movie>){
        addItems(movies)
        notifyItemInserted(items.size)
    }


    override fun layout(item: Any?): Int {
        return R.layout.movie_vertical_layout
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return MovieViewHolder(view, delegate)
    }
}