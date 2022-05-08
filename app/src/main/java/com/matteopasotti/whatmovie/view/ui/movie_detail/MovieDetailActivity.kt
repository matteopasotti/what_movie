package com.matteopasotti.whatmovie.view.ui.movie_detail

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.ImageView
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.matteopasotti.core.BackButton
import com.matteopasotti.core.CircleImage
import com.matteopasotti.core.MovieCard
import com.matteopasotti.core.MovieImage
import com.matteopasotti.whatmovie.R
import com.matteopasotti.whatmovie.compose.Pink
import com.matteopasotti.whatmovie.compose.Typography
import com.matteopasotti.whatmovie.compose.WhatMovieComposeTheme
import com.matteopasotti.whatmovie.compose.ui.AboutLayout
import com.matteopasotti.whatmovie.compose.ui.IconText
import com.matteopasotti.whatmovie.compose.ui.MoviesList
import com.matteopasotti.whatmovie.compose.ui.RatingView
import com.matteopasotti.whatmovie.model.ActorDomainModel
import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.view.ui.actor_detail.ActorDetailActivity
import com.matteopasotti.whatmovie.view.viewholder.MovieCastViewHolder
import com.matteopasotti.whatmovie.view.viewholder.MovieViewHolder
import org.koin.androidx.viewmodel.ext.android.viewModel


class MovieDetailActivity : AppCompatActivity(),
    MovieCastViewHolder.Delegate {

    private val viewModel: MovieDetailViewModel by viewModel()

    companion object {
        const val MOVIE = "movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhatMovieComposeTheme {
                intent.getParcelableExtra<MovieDomainModel>(MOVIE)?.let { movie ->
                    viewModel.initScreen(movie)
                    val state by viewModel.viewState.observeAsState(initial = MovieDetailState.Idle)
                    when (state) {
                        is MovieDetailState.Idle -> {
                            //progress.visibility = View.VISIBLE
                        }
                        is MovieDetailState.Error -> {
                            //progress.visibility = View.GONE
                        }
                        is MovieDetailState.Content -> {
                            MovieDetailScreen(state as MovieDetailState.Content)
                        }
                    }
                }

            }
        }

    }

    @Composable
    fun MovieDetailScreen(content: MovieDetailState.Content) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            viewModel.movie?.let { movie ->
                HeaderMovieDetail(
                    vote = content.moviesDetail.vote_average,
                    duration = content.moviesDetail.duration,
                    movie.title!!,
                    movie.backdrop_path)

                Spacer(
                    modifier = Modifier
                        .height(20.dp)
                        .fillMaxWidth()
                )

                CastLayout(cast = content.cast) {}
                Spacer(
                    modifier = Modifier
                        .height(20.dp)
                        .fillMaxWidth()
                )
                AboutLayout(movieDetail = content.moviesDetail)
                Spacer(
                    modifier = Modifier
                        .height(16.dp)
                        .fillMaxWidth()
                )
                MoviesList("Similar Movies", content.similarMovies) {
                    onItemClick(it)
                }
                Spacer(
                    modifier = Modifier
                        .height(16.dp)
                        .fillMaxWidth()
                )
                MoviesList("Recommended Movies", content.recommendedMovies) {
                    onItemClick(it)
                }
            }

        }
    }

    @Composable
    private fun HeaderMovieDetail(vote: Float, duration: String, title: String, img: String?) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {

            val (backdropImage, titleRef, rating, backBtn, durationRef) = createRefs()


            MovieImage(
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1f)
                    .constrainAs(backdropImage) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }, imageUrl = img.orEmpty()
            )

            BackButton(modifier = Modifier.constrainAs(backBtn) {
                start.linkTo(parent.start, 16.dp)
                top.linkTo(parent.top, 16.dp)
            }) {
                onBackPressed()
            }

            Text(
                text = title,
                color = Color.White,
                style = Typography.h1,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .constrainAs(titleRef) {
                        end.linkTo(rating.start)
                        start.linkTo(parent.start, 16.dp)
                        top.linkTo(rating.top)
                        width = Dimension.fillToConstraints
                    })

            RatingView(
                rating = vote,
                modifier = Modifier.constrainAs(rating) {
                    end.linkTo(parent.end, 16.dp)
                    bottom.linkTo(backdropImage.bottom, 16.dp)
                })

            IconText(
                text = duration,
                drawable = R.drawable.ic_clock,
                textColor = Pink,
                modifier = Modifier.constrainAs(durationRef) {
                    top.linkTo(titleRef.bottom)
                    start.linkTo(parent.start, 16.dp)
                    bottom.linkTo(parent.bottom, 3.dp)
                }
            )

//                    BlurryBackground(modifier = Modifier
//                        .fillMaxSize()
//                        .constrainAs(blurryBg) {
//                            start.linkTo(parent.start)
//                            top.linkTo(parent.top)
//                            end.linkTo(parent.end)
//                            bottom.linkTo(backdropImage.bottom)
//                        })


        }
    }

    @Composable
    private fun CastLayout(cast: List<ActorDomainModel>, onActorClicked: (ActorDomainModel) -> Unit) {
        Text(text = "The Cast", color = Color.White, style = Typography.body1, modifier = Modifier.padding(start = 16.dp))
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .fillMaxWidth()
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(start = 16.dp)
        ) {
            items(cast) {
                CircleImage(imageUrl = it.profileImage)
            }
        }
    }


    private fun onItemClick(movie: MovieDomainModel) {
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