package com.matteopasotti.whatmovie.view.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.matteopasotti.whatmovie.R
import com.matteopasotti.whatmovie.databinding.FragmentMovieGalleryBinding
import com.matteopasotti.whatmovie.model.Movie
import com.matteopasotti.whatmovie.view.adapter.MoviesAdapter
import com.matteopasotti.whatmovie.view.viewholder.MovieViewHolder
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeMoviesGallery : Fragment(), MovieViewHolder.Delegate {

    private lateinit var binding: FragmentMovieGalleryBinding

    private lateinit var adapter: MoviesAdapter

    private val viewModel: HomeGalleryMoviesViewModel by viewModel()

    private var section: String? = null

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

        initView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeViewModel()
    }

    private fun initView() {
        adapter = MoviesAdapter(this)
        val linearLayoutManager = LinearLayoutManager( context, LinearLayoutManager.HORIZONTAL, false)
        binding.movieList.layoutManager = linearLayoutManager

        section = arguments?.getString(HOME_CATEGORY)
        binding.movieCategoryNameBg.text = section
        binding.movieCategoryNameTop.text = section
    }

    private fun observeViewModel() {
        viewModel.movies.observe(this, Observer {
            it?.let {
                adapter.updateItems(it)
            }
        })

        viewModel.getMovies(section)
    }

    override fun onItemClick(movie: Movie) {
        Log.d("", "")
    }
}