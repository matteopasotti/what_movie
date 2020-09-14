package com.matteopasotti.whatmovie.view.ui.movie_detail

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.matteopasotti.whatmovie.R
import com.matteopasotti.whatmovie.databinding.ActivityMovieDetailBinding
import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.util.Utils
import com.matteopasotti.whatmovie.view.adapter.MovieCastAdapter
import com.matteopasotti.whatmovie.view.adapter.MoviesAdapter
import com.matteopasotti.whatmovie.view.viewholder.MovieViewHolder
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailActivity : AppCompatActivity(), RequestListener<Bitmap>, MovieViewHolder.Delegate {

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMovieDetailBinding>(
            this,
            R.layout.activity_movie_detail
        )
    }

    private val viewModel: MovieDetailViewModel by viewModel()

    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var castAdapter: MovieCastAdapter

    companion object {
        const val MOVIE = "movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.movie = intent.getParcelableExtra<MovieDomainModel>(MOVIE)
            viewModel.movie?.let {
                binding.movie = viewModel.movie
                initView()
                observeViewModel()
            }

        }

    }

    private fun observeViewModel() {

        viewModel.isLoading().observe(this, Observer { isLoading ->
            isLoading?.let {
                //binding.progressAnimation.visibility = if(isLoading) View.VISIBLE else View.GONE
            }
        })

        viewModel.isError().observe(this, Observer {
            if(it) {
                //error
            }
        })

        viewModel.recommendedMovies.observe(this , Observer {
            it?.let {
                binding.recommendedLayout.visibility = View.VISIBLE
                moviesAdapter.updateItems(it)
            }
        })

        viewModel.cast.observe(this, Observer {
            it?.let {
                binding.castLayout.visibility = View.VISIBLE
                castAdapter.updateItems(it)
            }
        })

        viewModel.movieDetails.observe(this, Observer {
            it?.let {
                binding.movieDetailLayout.detail = it
            }
        })

        viewModel.getData()
    }


    private fun initView() {

        moviesAdapter = MoviesAdapter(this)

        binding.rvRecommendedMovies.apply {
            setHasFixedSize(true)
            val manager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            layoutManager = manager
            adapter = moviesAdapter
        }

        castAdapter = MovieCastAdapter()
        binding.rvCast.apply {
            setHasFixedSize(true)
            val manager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            layoutManager = manager
            adapter = castAdapter
        }

        applyGradientBgColor()
    }

    private fun applyGradientBgColor() {

        val centerCrop = RequestOptions().centerCrop()

        Glide.with(this)
            .asBitmap()
            .apply(centerCrop)
            .load(viewModel.movie!!.poster_path)
            .listener(this).into(binding.backdropImage)
    }

    override fun onLoadFailed(
        e: GlideException?,
        model: Any?,
        target: Target<Bitmap>?,
        isFirstResource: Boolean
    ): Boolean {
        return false
    }

    override fun onResourceReady(
        resource: Bitmap?,
        model: Any?,
        target: Target<Bitmap>?,
        dataSource: DataSource?,
        isFirstResource: Boolean
    ): Boolean {
        Utils.getGradientColor(resources, resource!!, callkback = object :
            Utils.GradientBackgroundCallkback {
            override fun backgroundReady(bg: GradientDrawable) {
                binding.viewBackground.background = bg
            }
        })

        return false
    }

    override fun onItemClick(movie: MovieDomainModel) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra(MovieDetailActivity.MOVIE, movie as Parcelable)
        startActivity(intent)
    }
}