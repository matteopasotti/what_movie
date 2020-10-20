package com.matteopasotti.whatmovie.view.adapter

import android.view.View
import com.matteopasotti.whatmovie.R
import com.matteopasotti.whatmovie.model.Movie
import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.view.viewholder.BaseViewHolder
import com.matteopasotti.whatmovie.view.viewholder.MovieViewHolder

class MoviesAdapter(private val delegate: MovieViewHolder.Delegate): BaseAdapter() {

    init {
        addItems(ArrayList<MovieDomainModel>())
    }

    fun updateItems(movies: List<MovieDomainModel>){
        addItems(movies)
    }


    override fun layout(item: Any?): Int {
        return R.layout.movie_vertical_layout
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return MovieViewHolder(view, delegate)
    }
}