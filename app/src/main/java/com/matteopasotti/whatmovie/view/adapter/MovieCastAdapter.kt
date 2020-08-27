package com.matteopasotti.whatmovie.view.adapter

import android.view.View
import com.matteopasotti.whatmovie.R
import com.matteopasotti.whatmovie.model.ActorDomainModel
import com.matteopasotti.whatmovie.view.viewholder.BaseViewHolder
import com.matteopasotti.whatmovie.view.viewholder.MovieCastViewHolder

class MovieCastAdapter(): BaseAdapter() {

    init {
        addItems(ArrayList<ActorDomainModel>())
    }

    fun updateItems(actors: List<ActorDomainModel>){
        addItems(actors)
        notifyItemInserted(items.size)
    }

    override fun layout(item: Any?): Int {
        return R.layout.movie_cast_item_layout
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return MovieCastViewHolder(view)
    }
}