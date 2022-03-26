package com.matteopasotti.whatmovie.view.ui.movie_detail

import androidx.lifecycle.viewModelScope
import com.matteopasotti.whatmovie.model.ActorDomainModel
import com.matteopasotti.whatmovie.model.MovieDetailDomainModel
import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.usecase.MovieDetailsUseCase
import com.matteopasotti.whatmovie.usecase.MovieCreditsDomainModel
import com.matteopasotti.whatmovie.usecase.MovieDetailVModel
import com.matteopasotti.whatmovie.usecase.MoviesDomainModel
import com.matteopasotti.whatmovie.view.BaseStateViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

sealed class MovieDetailState {
    object Idle : MovieDetailState()
    object Error : MovieDetailState()
    data class Content(
        val similarMovies: List<MovieDomainModel>,
        val recommendedMovies: List<MovieDomainModel>,
        val cast: List<ActorDomainModel>,
        val moviesDetail: MovieDetailDomainModel
    ) : MovieDetailState()
}

sealed class MovieDetailEvents {
    data class MovieClicked(val movieId: String) : MovieDetailEvents()
    data class ActorClicked(val actorId: String) : MovieDetailEvents()
    data class HeartIconClicked(val movieId: String) : MovieDetailEvents()
}

class MovieDetailViewModel(private val movieDetailsUseCase: MovieDetailsUseCase) :
    BaseStateViewModel<MovieDetailState, MovieDetailEvents>(MovieDetailState.Idle) {

    var movie: MovieDomainModel? = null

    var movieDetail: MovieDetailDomainModel? = null


    fun getData() {
        movie?.let {
            getMovieDetails(movieId = it.id)
        }

    }

    private fun getMovieDetails(movieId: Int) {

        viewModelScope.launch {
            val recommendedMovieResponse =
                async { movieDetailsUseCase.getRecommendedMovies(movieId) }

            val similarMoviesResponse = async { movieDetailsUseCase.getSimilarMovies(movieId) }

            val creditResponse = async { movieDetailsUseCase.getMovieCredits(movieId) }

            val movieDetailResponse = async { movieDetailsUseCase.getMovieDetail(movieId) }

            updateUI(
                recommendedMovieResponse.await(),
                similarMoviesResponse.await(),
                creditResponse.await(),
                movieDetailResponse.await()
            )
        }

    }

    fun heartIconClicked(movieId: String) {
        emitEvent(MovieDetailEvents.HeartIconClicked(movieId))
    }

    private fun updateUI(
        recommendedMoviesResponse: MoviesDomainModel,
        similarMoviesResponse: MoviesDomainModel,
        creditResponse: MovieCreditsDomainModel,
        movieDetailResponse: MovieDetailVModel
    ) {

        // If we can not retrieve the movie details then we can not display this screen properly
        if (movieDetailResponse.errorMessage != null) {
            setState(MovieDetailState.Error)
        } else {
            setState(
                MovieDetailState.Content(
                    similarMovies = similarMoviesResponse.movies,
                    recommendedMovies = recommendedMoviesResponse.movies,
                    cast = creditResponse.cast ?: emptyList(),
                    moviesDetail = movieDetailResponse.movieDetail!!
                )
            )
        }
    }
}