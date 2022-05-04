package com.matteopasotti.whatmovie.view.ui.movie_detail

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.matteopasotti.whatmovie.R
import com.matteopasotti.whatmovie.databinding.ActivityMovieDetailBinding
import com.matteopasotti.whatmovie.model.ActorDomainModel
import com.matteopasotti.whatmovie.model.MovieDetailDomainModel
import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.view.adapter.MovieCastAdapter
import com.matteopasotti.whatmovie.view.adapter.MovieGenresAdapter
import com.matteopasotti.whatmovie.view.adapter.MoviesAdapter
import com.matteopasotti.whatmovie.view.custom.*
import com.matteopasotti.whatmovie.view.ui.actor_detail.ActorDetailActivity
import com.matteopasotti.whatmovie.view.ui.youtube.YoutubeFragment
import com.matteopasotti.whatmovie.view.viewholder.MovieCastViewHolder
import com.matteopasotti.whatmovie.view.viewholder.MovieViewHolder
import org.koin.androidx.viewmodel.ext.android.viewModel


class MovieDetailActivity : AppCompatActivity(), MovieViewHolder.Delegate,
    MovieCastViewHolder.Delegate {

    private val viewModel: MovieDetailViewModel by viewModel()

    private lateinit var recommendedMoviesAdapter: MoviesAdapter
    private lateinit var similarMoviesAdapter: MoviesAdapter
    private lateinit var castAdapter: MovieCastAdapter
    private lateinit var genresAdapter: MovieGenresAdapter

    private lateinit var youtubeContainer: FrameLayout
    private lateinit var castLayout: CustomLabelListView
    private lateinit var similarMoviesLayout: CustomLabelListView
    private lateinit var recommendedLayout: CustomLabelListView
    private lateinit var movieDuration: TextView
    private lateinit var movieDetailLayout: LinearLayout
    private lateinit var playButton: ImageView
    private lateinit var ratingView: RatingView
    private lateinit var genreList: NoScrollRecyclerView
    private lateinit var fabBackButton: CustomFabButton
    private lateinit var fabCheckbox: CheckBox
    private lateinit var backdropImage: AppCompatImageView

    companion object {
        const val MOVIE = "movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        youtubeContainer = findViewById(R.id.youtube_container)
        castLayout = findViewById(R.id.cast_layout)
        similarMoviesLayout = findViewById(R.id.similar_movies_layout)
        recommendedLayout = findViewById(R.id.recommended_layout)
        movieDuration = findViewById(R.id.movie_duration)
        movieDetailLayout = findViewById(R.id.movie_detail_layout)
        playButton = findViewById(R.id.play_button)
        ratingView = findViewById(R.id.rating_view)
        genreList = findViewById(R.id.genre_list)
        fabBackButton = findViewById(R.id.fab_back_button)
        fabCheckbox = findViewById(R.id.fab_checkbox)
        backdropImage = findViewById(R.id.backdrop_image)

        if (savedInstanceState == null) {
            viewModel.movie = intent.getParcelableExtra(MOVIE)
            viewModel.movie?.let {
                initView()
                observeViewModel()
            }

        }

    }

    private fun observeViewModel() {

        viewModel.viewState.observe(this, Observer { state ->
            when(state) {
                is MovieDetailState.Idle -> {

                }

                is MovieDetailState.Error -> {

                }

                is MovieDetailState.Content -> {
                    updateView(state.moviesDetail)
                    //cast
                    castLayout.addItems(state.cast)
                    castLayout.visibility = View.VISIBLE
                    //similar movies
                    similarMoviesLayout.addItems(state.similarMovies)
                    similarMoviesLayout.visibility = View.VISIBLE
                    //recommended movies
                    recommendedLayout.addItems(state.recommendedMovies)
                    recommendedLayout.visibility = View.VISIBLE
                }
            }

        })

        viewModel.getData()
    }

    private fun updateView(movieDetail: MovieDetailDomainModel) {
//        viewModel.movieDetail = movieDetail
//        movieDuration.text = movieDetail.duration
//        setBackdropImage(movieDetail.backdrop_path)
//        movieDetailLayout.detail = movieDetail
//        playButton.visibility =
//            if (movieDetail.videos.isNotEmpty()) View.VISIBLE else View.GONE
//        ratingView.setRatingScore(movieDetail.vote_average)
//        genresAdapter.updateItems(movieDetail.genres)
    }

    private fun setBackdropImage(imageUrl: String) {
        val cd = ColorDrawable(this.resources.getColor(R.color.grey, null))

        Glide
            .with(this)
            .load(imageUrl)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(cd)
            .into(backdropImage)
    }


    private fun initView() {

        genresAdapter = MovieGenresAdapter()
        genreList.layoutManager = NoScrollHorizontalLayoutManager(this)
        genreList.adapter = genresAdapter

        fabBackButton.setOnClickListener {
            onBackPressed()
        }

        recommendedMoviesAdapter = MoviesAdapter(this, this)
        recommendedLayout.setCustomLabelListView(
            getString(R.string.recommended_movies),
            recommendedMoviesAdapter
        )

        similarMoviesAdapter = MoviesAdapter(this, this)
        similarMoviesLayout.setCustomLabelListView(
            getString(R.string.similar_movies),
            similarMoviesAdapter
        )

        castAdapter = MovieCastAdapter(this)
        castLayout.setCustomLabelListView(getString(R.string.the_cast), castAdapter)

        playButton.setOnClickListener {

            youtubeContainer.visibility = View.VISIBLE

            val youtubeFragment =
                YoutubeFragment.newInstance(viewModel.movieDetail?.videos?.first()?.key!!)
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_movie_detail, youtubeFragment)
                .commit()

        }

        fabCheckbox.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                //viewModel.addFavCharacter(viewModel.character)
            } else {
                //viewModel.removeFavCharacter(viewModel.character)
            }
        }

    }

    override fun onItemClick(movie: MovieDomainModel) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra(MovieDetailActivity.MOVIE, movie as Parcelable)
        startActivity(intent)
    }

    override fun onActorClicked(actor: ActorDomainModel, view: View) {

        val img = view.findViewById<ImageView>(R.id.actor_image)

        val options = ActivityOptions.makeSceneTransitionAnimation(
            this,
            img,
            resources.getString(R.string.actor_image_transition)
        )

        val intent = Intent(this, ActorDetailActivity::class.java)
        intent.putExtra(ActorDetailActivity.INTENT_ACTOR, actor as Parcelable)
        startActivity(intent, options.toBundle())
    }
}