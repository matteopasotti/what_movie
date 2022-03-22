package com.matteopasotti.whatmovie.view.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.PagerSnapHelper
import com.matteopasotti.whatmovie.R
import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.view.adapter.CarouselAdapter
import com.matteopasotti.whatmovie.view.adapter.MoviesAdapter
import com.matteopasotti.whatmovie.view.ui.movie_detail.MovieDetailActivity
import com.matteopasotti.whatmovie.view.viewholder.MovieViewHolder
import kotlinx.android.synthetic.main.fragment_home_movies.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeMoviesFragment : Fragment(), MovieViewHolder.Delegate {

    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var moviesCinemaAdapter: MoviesAdapter
    private val carouselAdapter = CarouselAdapter(::carouselItemClicked)


    private val viewModel: HomeGalleryMoviesViewModel by viewModel()

    private var section: Int? = null

    companion object {

        private const val HOME_CATEGORY = "home_category"

        fun newInstance(homeCategory: Int): HomeMoviesFragment {
            val args = Bundle()
            args.putInt(HOME_CATEGORY, homeCategory)
            val fragment = HomeMoviesFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        observeViewModel()
    }

    private fun initView() {

        carousel.run {
            PagerSnapHelper().attachToRecyclerView(this)
            adapter = carouselAdapter
        }

        moviesAdapter = MoviesAdapter(context!!, this)
        popular_movies_layout.setCustomLabelListView(
            getString(R.string.popular_movies),
            moviesAdapter
        )

        moviesCinemaAdapter = MoviesAdapter(context!!, this)
        movies_cinema_layout.setCustomLabelListView(
            getString(R.string.in_theaters),
            moviesCinemaAdapter
        )
    }

    private fun observeViewModel() {

        viewModel.viewState.observe(viewLifecycleOwner, Observer { state ->
            when(state) {
                is HomeMovieGalleryState.Idle -> {
                    progress.visibility = View.VISIBLE
                }
                is HomeMovieGalleryState.Error -> {
                    progress.visibility = View.GONE
                }
                is HomeMovieGalleryState.Content -> {
                    progress.visibility = View.GONE
                    showPopularMovies(state.popularMovies)
                    showTrendingMovies(state.trendingMovies)
                    showMoviesAtCinema(state.moviesAtCinema)
                }
            }
        })
    }

    private fun showMoviesAtCinema(movies: List<MovieDomainModel>) {
        movies_cinema_layout.visibility = View.VISIBLE
        moviesCinemaAdapter.updateItems(movies)
    }

    private fun showPopularMovies(movies: List<MovieDomainModel>) {
        popular_movies_layout.visibility = View.VISIBLE
        moviesAdapter.updateItems(movies)
    }

    private fun showTrendingMovies(movies: List<MovieDomainModel>) {
        carousel.visibility = View.VISIBLE
        carouselAdapter.setItems(movies)
        carousel.resumeAutoScroll()
    }

    private fun carouselItemClicked(movie: MovieDomainModel) {
        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra(MovieDetailActivity.MOVIE, movie as Parcelable)
        startActivity(intent)
    }


    override fun onItemClick(movie: MovieDomainModel) {
        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra(MovieDetailActivity.MOVIE, movie as Parcelable)
        startActivity(intent)
    }
}