package com.matteopasotti.whatmovie.view.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.matteopasotti.whatmovie.R
import com.matteopasotti.whatmovie.databinding.FragmentMovieGalleryBinding
import com.matteopasotti.whatmovie.model.Movie
import com.matteopasotti.whatmovie.view.adapter.MoviesAdapter
import com.matteopasotti.whatmovie.view.viewholder.MovieViewHolder

class HomeMoviesGallery : Fragment(), MovieViewHolder.Delegate {

    private lateinit var binding: FragmentMovieGalleryBinding

    private lateinit var adapter: MoviesAdapter

    companion object {

        private const val HOME_CATEGORY = "home_category"

        fun newInstance(homeCategory: HomeMovieCategoryConstants): HomeMoviesGallery{
            val args = Bundle()
            args.putString(HOME_CATEGORY, homeCategory.toString())
            val fragment = HomeMoviesGallery()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_gallery, container, false)

        return binding.root
    }

    private fun initView() {
        adapter = MoviesAdapter(this)
    }

    override fun onItemClick(movie: Movie) {
        Log.d("", "")
    }
}