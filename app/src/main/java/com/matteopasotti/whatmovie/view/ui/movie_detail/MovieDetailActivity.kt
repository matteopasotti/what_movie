package com.matteopasotti.whatmovie.view.ui.movie_detail

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Pair
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.matteopasotti.whatmovie.R
import com.matteopasotti.whatmovie.databinding.ActivityMovieDetailBinding
import com.matteopasotti.whatmovie.model.ActorDomainModel
import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.view.adapter.MovieCastAdapter
import com.matteopasotti.whatmovie.view.adapter.MoviesAdapter
import com.matteopasotti.whatmovie.view.ui.actor_detail.ActorDetailActivity
import com.matteopasotti.whatmovie.view.viewholder.MovieCastViewHolder
import com.matteopasotti.whatmovie.view.viewholder.MovieViewHolder
import kotlinx.android.synthetic.main.movie_cast_item_layout.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MovieDetailActivity : AppCompatActivity(), MovieViewHolder.Delegate, MovieCastViewHolder.Delegate {

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMovieDetailBinding>(
            this,
            R.layout.activity_movie_detail
        )
    }

    private val viewModel: MovieDetailViewModel by viewModel()

    private lateinit var recommendedMoviesAdapter: MoviesAdapter
    private lateinit var similarMoviesAdapter: MoviesAdapter
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
                binding.recommendedLayout.addItems(it)
                binding.recommendedLayout.visibility = View.VISIBLE
            }
        })

        viewModel.similarMovies.observe(this , Observer {
            it?.let {
                binding.similarMoviesLayout.addItems(it)
                binding.similarMoviesLayout.visibility = View.VISIBLE
            }
        })

        viewModel.cast.observe(this, Observer {
            it?.let {
                binding.castLayout.addItems(it)
                binding.castLayout.visibility = View.VISIBLE
            }
        })

        viewModel.movieDetails.observe(this, Observer {
            it?.let {
                binding.movieDetailLayout.detail = it
                binding.ratingView.setRatingScore(it.vote_average)
            }
        })

        viewModel.getData()
    }


    private fun initView() {

        recommendedMoviesAdapter = MoviesAdapter(this)
        binding.recommendedLayout.setCustomLabelListView(getString(R.string.recommended_movies), recommendedMoviesAdapter)

        similarMoviesAdapter = MoviesAdapter(this)
        binding.similarMoviesLayout.setCustomLabelListView(getString(R.string.similar_movies), similarMoviesAdapter)

        castAdapter = MovieCastAdapter(this)
        binding.castLayout.setCustomLabelListView(getString(R.string.the_cast), castAdapter)
    }

    override fun onItemClick(movie: MovieDomainModel) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra(MovieDetailActivity.MOVIE, movie as Parcelable)
        startActivity(intent)
    }

    override fun onActorClicked(actor: ActorDomainModel, view: View) {

        val img = view.actor_image as ImageView

        val options = ActivityOptions.makeSceneTransitionAnimation(this, img, resources.getString(R.string.actor_image_transition))

        val intent = Intent(this, ActorDetailActivity::class.java)
        intent.putExtra(ActorDetailActivity.INTENT_ACTOR, actor as Parcelable)
        startActivity(intent, options.toBundle())
    }
}