package com.matteopasotti.whatmovie.view.ui.movie_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.usecase.GetMovieDetailsUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val getMovieDetailsUseCase: GetMovieDetailsUseCase): ViewModel() {

    var movie: MovieDomainModel? = null

    private val _recommendedMovies = MutableLiveData<List<MovieDomainModel>>()
    val recommendedMovies: LiveData<List<MovieDomainModel>> = _recommendedMovies

    fun getData() {
        movie?.let {
            viewModelScope.launch {
                getMovieDetails(movieId = it.id)
            }
        }

    }

    suspend fun getMovieDetails(movieId: Int) {
        try {
            val recommendedMovieResponse = viewModelScope.async {
                getMovieDetailsUseCase.getRecommendedMovie(movieId)
            }

            updateUI(recommendedMovieResponse.await())
        } catch (e: Throwable) {
            //TODO update status and show error
        }
    }

    private fun updateUI(recommendedMovieResponse: GetMovieDetailsUseCase.Result) {
        when(recommendedMovieResponse) {
            is GetMovieDetailsUseCase.Result.Success -> {
                if(recommendedMovieResponse.data.isEmpty()) {
                    //TODO no recommended movies received, handle this scenario too
                    //isLoadingLiveData.value = false
                    //isErrorLiveData.value = true
                } else {
                    //isLoadingLiveData.value = false
                    //popularMoviesLiveData.value = result.data
                    _recommendedMovies.postValue(recommendedMovieResponse.data)
                }
            }
        }
    }
}