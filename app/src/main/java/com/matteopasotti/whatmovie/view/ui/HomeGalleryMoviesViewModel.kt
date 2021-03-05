package com.matteopasotti.whatmovie.view.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matteopasotti.whatmovie.api.Result
import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.usecase.GetPopularMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent

class HomeGalleryMoviesViewModel(
    private val useCase: GetPopularMoviesUseCase) : ViewModel(), KoinComponent {

    private val _isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoadingLiveData

    private val _isErrorLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    val isError: LiveData<Boolean> = _isErrorLiveData

    private val _popularMoviesLiveData: MutableLiveData<List<MovieDomainModel>> = MutableLiveData()
    val popularMovies: LiveData<List<MovieDomainModel>> = _popularMoviesLiveData

    private val _moviesInCinemaLiveData: MutableLiveData<List<MovieDomainModel>> = MutableLiveData()
    val moviesInCinema: LiveData<List<MovieDomainModel>> = _moviesInCinemaLiveData

    fun getMovies() {
        viewModelScope.launch {
            val popularMovies = viewModelScope.async(Dispatchers.IO) { useCase.getPopularMovies() }

            val moviesAtCinema = viewModelScope.async(Dispatchers.IO) { useCase.getMoviesAtCinema() }

            updateUI(popularMovies.await(), moviesAtCinema.await())
        }
    }

    private fun updateUI(popularMovies: Result<Any>, moviesAtCinema: Result<Any>) {
        handleMoviesAtCinema(moviesAtCinema)
        handlePopularMoviesResponse(popularMovies)
    }

    private fun handleMoviesAtCinema(response: Result<Any>) {
        when(response) {
            is Result.Success -> {
                val movies: List<MovieDomainModel>?  = response.data as List<MovieDomainModel>?
                if(movies.isNullOrEmpty()) {
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
        when(response) {
            is Result.Success -> {
                val movies: List<MovieDomainModel>?  = response.data as List<MovieDomainModel>?
                if(movies.isNullOrEmpty()) {
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