package com.matteopasotti.whatmovie.view.ui

import androidx.lifecycle.*
import com.matteopasotti.whatmovie.model.Movie
import com.matteopasotti.whatmovie.repository.MovieRepository
import kotlinx.coroutines.launch

open class HomeGalleryMoviesViewModel(private val movieRepository: MovieRepository) : ViewModel() {

   /* private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies*/

    open val popularMovies: LiveData<List<Movie>> = Transformations.map(movieRepository.popularMovies) {
        movies ->
        if(movies.isNullOrEmpty()) {
            //we do not have popular movies in db
            getPopularMovies(category)
        } else {
            isLoadingLiveData.value = false
        }

        return@map movies
    }

    var category: Int? = null

    private lateinit var isLoadingLiveData: MutableLiveData<Boolean>

    private lateinit var isErrorLiveData: MutableLiveData<Boolean>

    fun isLoading(): LiveData<Boolean> {
        if(!::isLoadingLiveData.isInitialized) {
            isLoadingLiveData = MutableLiveData()
            isLoadingLiveData.value = true
        }

        return isLoadingLiveData
    }

    fun isError(): LiveData<Boolean> {
        if(!::isErrorLiveData.isInitialized) {
            isErrorLiveData = MutableLiveData()
            isErrorLiveData.value = false
        }

        return isErrorLiveData
    }

    fun getPopularMovies(category: Int?) {
        viewModelScope.launch {
            try {
                isLoadingLiveData.value = true
                when (category) {
                    HomeMovieCategoryConstants.POPULARS -> movieRepository.getPopMovies()
                    else -> movieRepository.getPopMovies()
                }
            } finally {
                isLoadingLiveData.value = false
            }
        }
    }

    /*fun getMovies(category: Int?) {
        viewModelScope.launch {
            try {
                isLoadingLiveData.value = true
                _movies.postValue(
                    when (category) {
                        HomeMovieCategoryConstants.POPULARS -> movieRepository.getPopularMovies()
                        else -> movieRepository.getPopularMovies()
                    }
                )
            } finally {
                isLoadingLiveData.value = false
            }

        }
    }*/
}