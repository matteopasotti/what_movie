package com.matteopasotti.whatmovie.view.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matteopasotti.whatmovie.model.Movie
import com.matteopasotti.whatmovie.repository.MovieRepository
import kotlinx.coroutines.launch

class HomeGalleryMoviesViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    fun getMovies(category: String?) {
        viewModelScope.launch {
            _movies.postValue(
                when (category) {
                    "popular" -> movieRepository.getPopularMovies()
                    else -> movieRepository.getPopularMovies()
                }
            )
        }
    }
}