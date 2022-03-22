package com.matteopasotti.whatmovie.view.ui.home

import androidx.lifecycle.viewModelScope
import com.matteopasotti.whatmovie.api.Result
import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.model.response.BasicMovieResponse
import com.matteopasotti.whatmovie.model.toDomainModel
import com.matteopasotti.whatmovie.usecase.GetPopularMoviesUseCase
import com.matteopasotti.whatmovie.usecase.MoviesDomainModel
import com.matteopasotti.whatmovie.view.BaseStateViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent

sealed class HomeMovieGalleryState {
    object Idle : HomeMovieGalleryState()
    object Error : HomeMovieGalleryState()
    data class Content(
        val popularMovies: List<MovieDomainModel>,
        val moviesAtCinema: List<MovieDomainModel>,
        val trendingMovies: List<MovieDomainModel>
    ) : HomeMovieGalleryState()
}

sealed class HomeMovieGalleryEvents {
    data class MovieClicked(val movieId: String) : HomeMovieGalleryEvents()
}

class HomeGalleryMoviesViewModel(
    private val useCase: GetPopularMoviesUseCase
) : BaseStateViewModel<HomeMovieGalleryState, HomeMovieGalleryEvents>(HomeMovieGalleryState.Idle),
    KoinComponent {

    fun getMovies() {
        viewModelScope.launch {
            val trending = viewModelScope.async(Dispatchers.IO) { useCase.getTrendingOfTheWeek() }

            val popularMovies = viewModelScope.async(Dispatchers.IO) { useCase.getPopularMovies() }

            val moviesAtCinema =
                viewModelScope.async(Dispatchers.IO) { useCase.getMoviesAtCinema() }

            updateUI(popularMovies.await(), moviesAtCinema.await(), trending.await())
        }
    }

    private fun updateUI(
        popularMoviesResponse: MoviesDomainModel,
        moviesAtCinemaResponse: MoviesDomainModel,
        trendingMoviesResponse: MoviesDomainModel
    ) {
        if (popularMoviesResponse.errorMessage != null ||
            moviesAtCinemaResponse.errorMessage != null ||
            trendingMoviesResponse.errorMessage != null
        ) {
            setState(HomeMovieGalleryState.Error)
        } else {
            setState(
                HomeMovieGalleryState.Content(
                    trendingMovies = trendingMoviesResponse.movies,
                    popularMovies = popularMoviesResponse.movies,
                    moviesAtCinema = moviesAtCinemaResponse.movies
                )
            )
        }
    }
}