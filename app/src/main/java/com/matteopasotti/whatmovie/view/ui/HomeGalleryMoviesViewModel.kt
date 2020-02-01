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

    fun getMovies(category: Int?) {
        viewModelScope.launch {
            _movies.postValue(
                when (category) {
                    HomeMovieCategoryConstants.POPULARS -> movieRepository.getPopularMovies()
                    else -> movieRepository.getPopularMovies()
                }
            )
        }
    }
}