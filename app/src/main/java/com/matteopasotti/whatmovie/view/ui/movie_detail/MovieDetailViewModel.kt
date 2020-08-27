package com.matteopasotti.whatmovie.view.ui.movie_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matteopasotti.whatmovie.api.Result
import com.matteopasotti.whatmovie.model.ActorDomainModel
import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.model.response.MovieCreditResponse
import com.matteopasotti.whatmovie.usecase.GetMovieDetailsUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jetbrains.annotations.TestOnly

class MovieDetailViewModel(private val getMovieDetailsUseCase: GetMovieDetailsUseCase): ViewModel() {

    var movie: MovieDomainModel? = null

    private val _recommendedMovies = MutableLiveData<List<MovieDomainModel>>()
    val recommendedMovies: LiveData<List<MovieDomainModel>> = _recommendedMovies

    private val _cast = MutableLiveData<List<ActorDomainModel>>()
    val cast: LiveData<List<ActorDomainModel>> = _cast

    private lateinit var isLoadingLiveData: MutableLiveData<Boolean>

    private lateinit var isErrorLiveData: MutableLiveData<Boolean>



    fun getData() {
        movie?.let {
            viewModelScope.launch {
                getMovieDetails(movieId = it.id)
            }
        }

    }

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

    private suspend fun getMovieDetails(movieId: Int) {
        try {
            val recommendedMovieResponse = viewModelScope.async {
                getMovieDetailsUseCase.getRecommendedMovie(movieId)
            }

            val creditResponse = viewModelScope.async {
                getMovieDetailsUseCase.getMovieCredits(movieId)
            }

            updateUI(recommendedMovieResponse.await(), creditResponse.await())
        } catch (e: Throwable) {
            //TODO update status and show error
            isLoadingLiveData.value = false
            isErrorLiveData.value = true
        }
    }

    private fun updateUI(recommendedMoviesResponse: Result<Any>, creditResponse: Result<Any>) {
        when(recommendedMoviesResponse) {
            is Result.Success -> {
                val movies: List<MovieDomainModel>?  = recommendedMoviesResponse.data as List<MovieDomainModel>?
                if(movies.isNullOrEmpty()) {
                    //TODO no recommended movies received, handle this scenario too
                    isLoadingLiveData.value = false
                    isErrorLiveData.value = true
                } else {
                    isLoadingLiveData.value = false
                    _recommendedMovies.postValue(movies)
                }
            }

            is Result.Error -> {
                isLoadingLiveData.value = false
                isErrorLiveData.value = true
            }
        }

        when(creditResponse) {
            is Result.Success -> {
                val cast: List<ActorDomainModel> = creditResponse.data as List<ActorDomainModel>
                if(cast.isNullOrEmpty()) {
                    isLoadingLiveData.value = false
                    isErrorLiveData.value = true
                } else {
                    isLoadingLiveData.value = false
                    _cast.postValue(cast)
                }
            }

            is Result.Error -> {
                isLoadingLiveData.value = false
                isErrorLiveData.value = true
            }
        }
    }
}