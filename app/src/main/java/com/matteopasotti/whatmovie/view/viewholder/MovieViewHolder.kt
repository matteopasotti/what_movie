package com.matteopasotti.whatmovie.view.viewholder

import android.view.View
import androidx.databinding.DataBindingUtil
import com.matteopasotti.whatmovie.databinding.MovieVerticalLayoutBinding
import com.matteopasotti.whatmovie.model.MovieDomainModel

class MovieViewHolder(view: View, val delegate: Delegate) : BaseViewHolder(view) {

    private val binding by lazy { DataBindingUtil.bind<MovieVerticalLayoutBinding>(view) }

    private lateinit var movie: MovieDomainModel

    interface Delegate {
        fun onItemClick(movie: MovieDomainModel)
    }

    override fun bindData(data: Any?) {
        if(data is MovieDomainModel) {
            movie = data
            binding.apply {
                binding?.movie = data
                binding?.image = movie.poster_path
                binding?.executePendingBindings()
            }
        }
    }

    override fun onClick(v: View?) {
        delegate.onItemClick(movie)
    }


}