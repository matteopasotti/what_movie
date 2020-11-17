package com.matteopasotti.whatmovie.view.viewholder

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.matteopasotti.whatmovie.R
import com.matteopasotti.whatmovie.databinding.MovieItemHomeLayoutBinding
import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.util.toPx

class MovieHomeViewHolder (val context: Context, view: View, private val delegate: MovieHomeViewHolder.Delegate) :
    BaseViewHolder(view) {

    private lateinit var movie: MovieDomainModel

    private val binding by lazy { DataBindingUtil.bind<MovieItemHomeLayoutBinding>(view) }


    interface Delegate {
        fun onItemClick(movie: MovieDomainModel)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun bindData(data: Any?) {
        if (data is MovieDomainModel) {
            movie = data
            binding.apply {
                binding?.movie = data
                val cd = ColorDrawable(context.resources.getColor(R.color.grey, null))

                Glide
                    .with(context)
                    .load(movie.poster_path)
                    .thumbnail(0.25f)
                    .centerCrop()
                    .skipMemoryCache(true)
                    .placeholder(cd)
                    .override(300, 480)
                    .into(binding!!.movieImage)
            }


        }
    }

    override fun onClick(v: View?) {
        delegate.onItemClick(movie)
    }
}