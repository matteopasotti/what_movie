package com.matteopasotti.whatmovie.view.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.matteopasotti.core.AutoSlider
import com.matteopasotti.core.MovieCard
import com.matteopasotti.whatmovie.R
import com.matteopasotti.whatmovie.compose.*
import com.matteopasotti.whatmovie.compose.ui.MoviesList
import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.view.adapter.CarouselAdapter
import com.matteopasotti.whatmovie.view.custom.AutoScrollableRecyclerView
import com.matteopasotti.whatmovie.view.ui.movie_detail.MovieDetailActivity
import com.matteopasotti.whatmovie.view.viewholder.MovieViewHolder
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeMoviesFragment : Fragment() {

    private val viewModel: HomeGalleryMoviesViewModel by viewModel()

    private val carouselAdapter = CarouselAdapter(::carouselItemClicked)

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
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                WhatMovieComposeTheme {
                    Scaffold {
                        val state by viewModel.viewState.observeAsState(initial = HomeMovieGalleryState.Idle)
                        when (state) {
                            is HomeMovieGalleryState.Idle -> {
                                //progress.visibility = View.VISIBLE
                            }
                            is HomeMovieGalleryState.Error -> {
                                //progress.visibility = View.GONE
                            }
                            is HomeMovieGalleryState.Content -> {
                                HomeMoviesScreen(state as HomeMovieGalleryState.Content)
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun HomeMoviesScreen(
        state: HomeMovieGalleryState.Content
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .background(color = primaryColor)
        ) {
            AndroidView(factory = { ctx ->
                AutoScrollableRecyclerView(ctx).apply {
                    layoutParams = RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                    layoutManager = LinearLayoutManager(ctx).apply {
                        orientation = LinearLayoutManager.HORIZONTAL
                    }
                    adapter = carouselAdapter
                    PagerSnapHelper().attachToRecyclerView(this)

                }
            })

            carouselAdapter.setItems(state.trendingMovies)

            Spacer(
                modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth()
            )
            MoviesList("Popular Movies", state.popularMovies) {
                onMovieClicked(it)
            }
            Spacer(
                modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth()
            )
            MoviesList("At Cinema", state.moviesAtCinema) {
                onMovieClicked(it)
            }
        }
    }

    private fun carouselItemClicked(movie: MovieDomainModel) {
        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra(MovieDetailActivity.MOVIE, movie as Parcelable)
        startActivity(intent)
    }

    private fun onMovieClicked(movie: MovieDomainModel) {
        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra(MovieDetailActivity.MOVIE, movie as Parcelable)
        startActivity(intent)
    }

}