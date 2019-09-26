package com.matteopasotti.whatmovie.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matteopasotti.whatmovie.model.Movie
import com.matteopasotti.whatmovie.repository.MovieRepositoryImpl
import kotlinx.coroutines.launch

class MainActivityViewModel(private val movieRepositoryImpl: MovieRepositoryImpl) : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    fun getPopularMovies() {
        viewModelScope.launch {
            _movies.postValue(movieRepositoryImpl.getPopularMovies())
        }
    }
}