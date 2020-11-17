package com.matteopasotti.whatmovie.view.viewholder

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.matteopasotti.whatmovie.R
import com.matteopasotti.whatmovie.model.MovieDomainModel

class MovieHomeViewHolderNormal(
    val context: Context,
    view: View,
    private val delegate: Delegate
) : RecyclerView.ViewHolder(view), View.OnClickListener {

    interface Delegate {
        fun onItemClick(movie: MovieDomainModel)
    }

    private var title: TextView = itemView.findViewById(R.id.movie_title)
    private var overview: TextView = itemView.findViewById(R.id.movie_overview)
    private var image: ImageView = itemView.findViewById(R.id.movie_image)
    private lateinit var movie: MovieDomainModel

    @RequiresApi(Build.VERSION_CODES.M)
    fun bindData(movie: MovieDomainModel) {
        this.movie = movie
        title.text = movie.title
        overview.text = movie.overview

        val cd = ColorDrawable(context.resources.getColor(R.color.grey, null))

        Glide
            .with(context)
            .load(movie.poster_path)
            .thumbnail(0.25f)
            .centerCrop()
            .skipMemoryCache(true)
            .placeholder(cd)
            .override(300, 480)
            .into(image)

        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        delegate.onItemClick(this.movie)
    }
}