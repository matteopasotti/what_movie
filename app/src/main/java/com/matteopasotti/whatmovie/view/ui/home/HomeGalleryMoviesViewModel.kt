package com.matteopasotti.whatmovie.view.ui.home

import androidx.lifecycle.viewModelScope
import com.matteopasotti.whatmovie.api.Result
import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.model.response.BasicMovieResponse
import com.matteopasotti.whatmovie.model.toDomainModel
import com.matteopasotti.whatmovie.usecase.GetPopularMoviesUseCase
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
        popularMoviesResponse: Result<BasicMovieResponse>,
        moviesAtCinemaResponse: Result<BasicMovieResponse>,
        trendingMoviesResponse: Result<BasicMovieResponse>
    ) {
        val trendingMovies = handleResponse(trendingMoviesResponse)
        val moviesAtCinema = handleResponse(moviesAtCinemaResponse)
        val popularMovies = handleResponse(popularMoviesResponse)

        if (trendingMovies != null && moviesAtCinema != null && popularMovies != null) {
            setState(
                HomeMovieGalleryState.Content(
                    trendingMovies = trendingMovies,
                    popularMovies = popularMovies,
                    moviesAtCinema = moviesAtCinema
                )
            )
        } else {
            setState(HomeMovieGalleryState.Error)
        }
    }

    private fun handleResponse(response: Result<BasicMovieResponse>): List<MovieDomainModel>? {
        return when (response) {
            is Result.Success -> {
                val movies = response.value.results
                return if (movies.isNullOrEmpty()) {
                    null
                } else {
                    movies.map { it.toDomainModel() }
                }
            }
            is Result.Error -> {
                null
            }
        }
    }
}