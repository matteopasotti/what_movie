package com.matteopasotti.whatmovie.view.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matteopasotti.whatmovie.api.Result
import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.model.response.BasicMovieResponse
import com.matteopasotti.whatmovie.model.toDomainModel
import com.matteopasotti.whatmovie.usecase.GetPopularMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent

class HomeGalleryMoviesViewModel(
    private val useCase: GetPopularMoviesUseCase
) : ViewModel(), KoinComponent {

    private val _isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoadingLiveData

    private val _isErrorLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    val isError: LiveData<Boolean> = _isErrorLiveData

    private val _popularMoviesLiveData: MutableLiveData<List<MovieDomainModel>> = MutableLiveData()
    val popularMovies: LiveData<List<MovieDomainModel>> = _popularMoviesLiveData

    private val _moviesInCinemaLiveData: MutableLiveData<List<MovieDomainModel>> = MutableLiveData()
    val moviesInCinema: LiveData<List<MovieDomainModel>> = _moviesInCinemaLiveData

    private val _trendingLiveData: MutableLiveData<List<MovieDomainModel>> = MutableLiveData()
    val trending: LiveData<List<MovieDomainModel>> = _trendingLiveData

    fun getMovies() {
        viewModelScope.launch {
            val trending = viewModelScope.async(Dispatchers.IO) { useCase.getTrendingOfTheWeek() }

            val popularMovies = viewModelScope.async(Dispatchers.IO) { useCase.getPopularMovies() }

            val moviesAtCinema =
                viewModelScope.async(Dispatchers.IO) { useCase.getMoviesAtCinema() }

            updateUI(popularMovies.await(), moviesAtCinema.await(), trending.await())
        }
    }

    private fun updateUI(popularMovies: Result<Any>, moviesAtCinema: Result<Any>, trending: Result<Any>) {
        handleTrendingOfTheWeekResponse(trending)
        handleMoviesAtCinema(moviesAtCinema)
        handlePopularMoviesResponse(popularMovies)
    }

    private fun handleTrendingOfTheWeekResponse(response: Result<Any>) {
        when (response) {
            is Result.Success -> {
                val resp = response.data as BasicMovieResponse
                val movies: List<MovieDomainModel>? = resp.results?.map { it.toDomainModel() }
                if (movies.isNullOrEmpty()) {
                    _isLoadingLiveData.value = false
                    _isErrorLiveData.value = true
                } else {
                    _isLoadingLiveData.value = false
                    _trendingLiveData.value = movies
                }
            }

            is Result.Error -> {
                _isLoadingLiveData.value = false
                _isErrorLiveData.value = true
            }
        }
    }

    private fun handleMoviesAtCinema(response: Result<Any>) {
        when (response) {
            is Result.Success -> {
                val resp = response.data as BasicMovieResponse
                val movies: List<MovieDomainModel>? = resp.results?.map { it.toDomainModel() }
                if (movies.isNullOrEmpty()) {
                    _isLoadingLiveData.value = false
                    _isErrorLiveData.value = true
                } else {
                    _isLoadingLiveData.value = false
                    _moviesInCinemaLiveData.value = movies
                }
            }

            is Result.Error -> {
                _isLoadingLiveData.value = false
                _isErrorLiveData.value = true
            }
        }
    }

    private fun handlePopularMoviesResponse(response: Result<Any>) {
        when (response) {
            is Result.Success -> {
                val resp = response.data as BasicMovieResponse
                val movies: List<MovieDomainModel>? = resp.results?.map { it.toDomainModel() }
                if (movies.isNullOrEmpty()) {
                    _isLoadingLiveData.value = false
                    _isErrorLiveData.value = true
                } else {
                    _isLoadingLiveData.value = false
                    _popularMoviesLiveData.value = movies
                }
            }

            is Result.Error -> {
                _isLoadingLiveData.value = false
                _isErrorLiveData.value = true
            }
        }
    }
}