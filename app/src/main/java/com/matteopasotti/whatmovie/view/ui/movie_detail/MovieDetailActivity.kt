package com.matteopasotti.whatmovie.view.ui.movie_detail

import android.graphics.Bitmap
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.matteopasotti.whatmovie.R
import com.matteopasotti.whatmovie.databinding.ActivityMovieDetailBinding
import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.util.ColorUtils
import com.matteopasotti.whatmovie.util.ColorUtils.Companion.GradientBackgroundCallkback
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailActivity : AppCompatActivity(), RequestListener<Bitmap> {

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMovieDetailBinding>(
            this,
            R.layout.activity_movie_detail
        )
    }

    private val viewModel: MovieDetailViewModel by viewModel()

    companion object {
        const val MOVIE = "movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.movie = intent.getParcelableExtra<MovieDomainModel>(MOVIE)
            viewModel.movie?.let {
                binding.movie = viewModel.movie
                applyGradientBgColor()
            }

        }

    }

    private fun applyGradientBgColor() {

        val centerCrop = RequestOptions().centerCrop()

        Glide.with(this)
            .asBitmap()
            .apply(centerCrop)
            .load(viewModel.movie!!.backdrop_path)
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
        ColorUtils.getGradientColor(resources, resource!!, callkback = object :
            GradientBackgroundCallkback {
            override fun backgroundReady(bg: GradientDrawable) {
                binding.viewBackground.background = bg
            }
        })

        return false
    }
}