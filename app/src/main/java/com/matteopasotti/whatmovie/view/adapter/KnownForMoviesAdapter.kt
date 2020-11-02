package com.matteopasotti.whatmovie.view.adapter

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.matteopasotti.whatmovie.R
import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.view.viewholder.BaseViewHolder
import com.matteopasotti.whatmovie.view.viewholder.MovieViewHolder

class KnownForMoviesAdapter(private val delegate: MovieViewHolder.Delegate): BaseAdapter() {

    private var lastPosition = -1

    init {
        addItems(ArrayList<MovieDomainModel>())
    }

    override fun layout(item: Any?): Int {
        return R.layout.movie_vertical_layout
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return MovieViewHolder(view, delegate)
    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {
        val data = getItemByPosition(position)

        try {
            viewHolder.bindData(data)
            val animation : Animation = AnimationUtils.loadAnimation(viewHolder.itemView.context , if (position > lastPosition) R.anim.right_to_left  else R.anim.left_to_right)
            viewHolder.itemView.startAnimation(animation)
            lastPosition = position
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}