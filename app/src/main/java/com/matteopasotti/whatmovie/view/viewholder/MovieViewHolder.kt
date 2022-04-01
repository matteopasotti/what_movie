package com.matteopasotti.whatmovie.view.viewholder

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.matteopasotti.whatmovie.R
import com.matteopasotti.whatmovie.databinding.MovieVerticalLayoutBinding
import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.util.toPx

class MovieViewHolder(val context: Context, view: View, private val delegate: Delegate) :
    BaseViewHolder(view) {

    private val binding by lazy { DataBindingUtil.bind<MovieVerticalLayoutBinding>(view) }

    private lateinit var movie: MovieDomainModel

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

                Log.d("Values", "width: " + 160.toPx() + " height: " + 210.toPx())

                Glide
                    .with(context)
                    .load(movie.poster_path)
                    .thumbnail(0.25f)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(cd)
                    .override(300, 400)
                    .into(binding!!.movieCover)
            }


        }
    }

    override fun onClick(v: View?) {
        delegate.onItemClick(movie)
    }


}