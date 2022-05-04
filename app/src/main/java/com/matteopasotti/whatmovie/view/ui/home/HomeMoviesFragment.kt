package com.matteopasotti.whatmovie.view.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import androidx.fragment.app.Fragment
import com.matteopasotti.core.AutoSlider
import com.matteopasotti.core.MovieCard
import com.matteopasotti.whatmovie.R
import com.matteopasotti.whatmovie.compose.WhatMovieComposeTheme
import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.view.ui.movie_detail.MovieDetailActivity
import com.matteopasotti.whatmovie.view.viewholder.MovieViewHolder
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeMoviesFragment : Fragment(), MovieViewHolder.Delegate {

    private val viewModel: HomeGalleryMoviesViewModel by viewModel()

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
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            AutoSlider(state.trendingMovies.map { it.poster_path }) {}
            MoviesList("Popular Movies", state.popularMovies)
            Spacer(modifier = Modifier
                .height(16.dp)
                .fillMaxWidth())
            MoviesList("At Cinema", state.moviesAtCinema)
        }
    }

    @Composable
    fun MoviesList(label: String, movies: List<MovieDomainModel>) {
        Column {
            Text(text = label, color = Color.White, style = MaterialTheme.typography.body1)
            Spacer(modifier = Modifier
                .height(16.dp)
                .fillMaxWidth())
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(movies) {
                    MovieCard(imageUrl = it.poster_path) {}
                }
            }
        }
        
    }


    override fun onItemClick(movie: MovieDomainModel) {
        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra(MovieDetailActivity.MOVIE, movie as Parcelable)
        startActivity(intent)
    }
}