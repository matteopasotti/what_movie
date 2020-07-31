package com.matteopasotti.whatmovie.view.ui

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.matteopasotti.whatmovie.R
import com.matteopasotti.whatmovie.databinding.FragmentMovieGalleryBinding
import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.util.MoviesUtils
import com.matteopasotti.whatmovie.view.adapter.GridAutofitLayoutManager
import com.matteopasotti.whatmovie.view.adapter.MoviesAdapter
import com.matteopasotti.whatmovie.view.ui.movie_detail.MovieDetailActivity
import com.matteopasotti.whatmovie.view.viewholder.MovieViewHolder
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeMoviesGalleryFragment : Fragment(), MovieViewHolder.Delegate {

    private lateinit var binding: FragmentMovieGalleryBinding

    private lateinit var moviesAdapter: MoviesAdapter

    private val viewModel: HomeGalleryMoviesViewModel by viewModel()

    private var section: Int? = null

    companion object {

        private const val HOME_CATEGORY = "home_category"

        fun newInstance(homeCategory: Int): HomeMoviesGalleryFragment{
            val args = Bundle()
            args.putInt(HOME_CATEGORY, homeCategory)
            val fragment = HomeMoviesGalleryFragment()
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
        moviesAdapter = MoviesAdapter(this)

        binding.movieList.apply {
            setHasFixedSize(true)
            val columnWidth = context.resources.getDimension(R.dimen.image_width).toInt()
            layoutManager =
                GridAutofitLayoutManager(
                    context,
                    columnWidth
                )
            adapter = moviesAdapter
        }


        section = arguments?.getInt(HOME_CATEGORY)
        binding.movieCategoryNameBg.text = MoviesUtils.getHomeSectionNameByCategory(section)
        binding.movieCategoryNameTop.text = MoviesUtils.getHomeSectionNameByCategory(section)
    }

    private fun observeViewModel() {

        viewModel.isLoading().observe(this, Observer { isLoading ->
            isLoading?.let {
                //binding.progressAnimation.visibility = if(isLoading) View.VISIBLE else View.GONE
            }
        })

        viewModel.getMovies().observe(this, Observer {
            it?.let {
                binding.movieList.visibility = View.VISIBLE
                moviesAdapter.updateItems(it)
            }
        })

        viewModel.isError().observe(this, Observer {
            if(it) {
                //error
            }
        })

        viewModel.getPopularMovies()
    }

    override fun onItemClick(movie: MovieDomainModel) {
        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra(MovieDetailActivity.MOVIE, movie as Parcelable)
        startActivity(intent)
    }
}