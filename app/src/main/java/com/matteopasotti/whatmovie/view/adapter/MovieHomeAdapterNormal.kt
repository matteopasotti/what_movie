package com.matteopasotti.whatmovie.view.adapter

import android.content.Context
import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.matteopasotti.whatmovie.R
import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.util.inflate
import com.matteopasotti.whatmovie.view.viewholder.MovieHomeViewHolderNormal

class MovieHomeAdapterNormal(
    private val context: Context,
    private val delegate: MovieHomeViewHolderNormal.Delegate
) : RecyclerView.Adapter<MovieHomeViewHolderNormal>() {

    private var movies : MutableList<MovieDomainModel> = mutableListOf()

    fun updateItems(items: List<MovieDomainModel>) {
        this.movies.addAll(items)
        notifyItemInserted(movies.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHomeViewHolderNormal {
        val v = parent.inflate(R.layout.movie_item_home)
        return MovieHomeViewHolderNormal(context, v, delegate)
    }

    override fun getItemCount(): Int = movies.size


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: MovieHomeViewHolderNormal, position: Int) {
        holder.bindData(movies[position])
    }
}