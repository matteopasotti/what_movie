package com.matteopasotti.whatmovie.view.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matteopasotti.whatmovie.api.Result
import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.usecase.GetPopularMoviesUseCase
import kotlinx.coroutines.launch

class HomeGalleryMoviesViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase) : ViewModel() {

    private lateinit var isLoadingLiveData: MutableLiveData<Boolean>

    private lateinit var isErrorLiveData: MutableLiveData<Boolean>

    private lateinit var popularMoviesLiveData: MutableLiveData<List<MovieDomainModel>>

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

    fun getMovies(): LiveData<List<MovieDomainModel>> {
        if(!::popularMoviesLiveData.isInitialized) {
            popularMoviesLiveData = MutableLiveData()
            popularMoviesLiveData.value = null
        }

        return popularMoviesLiveData
    }

    fun getPopularMovies() {
        viewModelScope.launch {
            getPopularMoviesUseCase.execute().also {
                result ->
                when(result) {
                    is Result.Success -> {
                        val movies: List<MovieDomainModel>?  = result.data as List<MovieDomainModel>?
                        if(movies.isNullOrEmpty()) {
                            isLoadingLiveData.value = false
                            isErrorLiveData.value = true
                        } else {
                            isLoadingLiveData.value = false
                            popularMoviesLiveData.value = movies
                        }
                    }

                   is Result.Error -> {
                       isLoadingLiveData.value = false
                       isErrorLiveData.value = true
                   }
                }


            }
        }
    }
}