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
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase) : ViewModel(), KoinComponent {

    private lateinit var isLoadingLiveData: MutableLiveData<Boolean>

    private lateinit var isErrorLiveData: MutableLiveData<Boolean>

    val popularMoviesLiveData: MutableLiveData<List<MovieDomainModel>> by lazy { MutableLiveData<List<MovieDomainModel>>() }

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

    fun getMovies(): LiveData<List<MovieDomainModel>> = popularMoviesLiveData

    fun getPopularMovies() {
        viewModelScope.launch {

            val result = viewModelScope.async { getPopularMoviesUseCase.execute() }

            updateUI(result.await())
        }
    }

    private fun updateUI(result: Result<Any>) {
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