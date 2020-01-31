package com.matteopasotti.whatmovie.view.viewholder

import android.view.View
import androidx.databinding.DataBindingUtil
import com.matteopasotti.whatmovie.databinding.MovieVerticalLayoutBinding
import com.matteopasotti.whatmovie.model.Movie

class MovieViewHolder(view: View, val delegate: Delegate) : BaseViewHolder(view) {

    private val binding by lazy { DataBindingUtil.bind<MovieVerticalLayoutBinding>(view) }

    private lateinit var movie: Movie

    interface Delegate {
        fun onItemClick(movie: Movie)
    }

    override fun bindData(data: Any?) {
        if(data is Movie) {
            movie = data
            binding.apply {
                binding?.movie = data
                binding?.executePendingBindings()
            }
        }
    }

    override fun onClick(v: View?) {
        delegate.onItemClick(movie)
    }


}