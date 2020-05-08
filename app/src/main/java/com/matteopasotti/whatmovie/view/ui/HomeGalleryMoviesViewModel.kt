package com.matteopasotti.whatmovie.view.ui

import androidx.lifecycle.*
import com.matteopasotti.whatmovie.model.Movie
import com.matteopasotti.whatmovie.usecase.GetPopularMoviesUseCase
import kotlinx.coroutines.launch

open class HomeGalleryMoviesViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase) : ViewModel() {

    private lateinit var isLoadingLiveData: MutableLiveData<Boolean>

    private lateinit var isErrorLiveData: MutableLiveData<Boolean>

    private lateinit var popularMoviesLiveData: MutableLiveData<List<Movie>>

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

    fun getMovies(): LiveData<List<Movie>> {
        if(!::popularMoviesLiveData.isInitialized) {
            popularMoviesLiveData = MutableLiveData()
            popularMoviesLiveData.value = null
        }

        return popularMoviesLiveData
    }

    fun getPopularMovies() {
        viewModelScope.launch {
            getPopularMoviesUseCase.execute().also {
                if(it.isNotEmpty()) {
                    isLoadingLiveData.value = false
                    popularMoviesLiveData.value = it
                } else {
                    isLoadingLiveData.value = false
                }
            }
        }
    }
}