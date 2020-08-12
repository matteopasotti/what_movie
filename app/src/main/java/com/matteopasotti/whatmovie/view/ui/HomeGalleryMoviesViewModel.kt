package com.matteopasotti.whatmovie.view.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.usecase.GetPopularMoviesUseCase
import kotlinx.coroutines.launch

class HomeGalleryMoviesViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase) : ViewModel() {

    private lateinit var isLoadingLiveData: MutableLiveData<Boolean>

    private lateinit var isErrorLiveData: MutableLiveData<Boolean>

    private lateinit var popularMoviesLiveData: MutableLiveData<List<MovieDomainModel>>

    //var page: Int = 1



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
                    is GetPopularMoviesUseCase.Result.Success -> {
                        if (result.data.isEmpty()) {
                            isLoadingLiveData.value = false
                            isErrorLiveData.value = true
                        } else {
                            isLoadingLiveData.value = false
                            popularMoviesLiveData.value = result.data
                        }
                    }

                    is GetPopularMoviesUseCase.Result.Error -> {
                        isLoadingLiveData.value = false
                        isErrorLiveData.value = true
                    }
                }


            }
        }
    }
}