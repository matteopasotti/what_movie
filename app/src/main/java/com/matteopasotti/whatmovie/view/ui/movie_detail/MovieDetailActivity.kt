package com.matteopasotti.whatmovie.view.ui.movie_detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.matteopasotti.whatmovie.R
import com.matteopasotti.whatmovie.databinding.ActivityMovieDetailBinding
import com.matteopasotti.whatmovie.model.MovieDomainModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailActivity: AppCompatActivity() {

    private val binding by lazy { DataBindingUtil.setContentView<ActivityMovieDetailBinding>(this, R.layout.activity_movie_detail) }

    private val viewModel: MovieDetailViewModel by viewModel()

    companion object {
        const val MOVIE = "movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val movie = intent.getParcelableExtra<MovieDomainModel>(MOVIE)
        binding.movie = movie
    }
}