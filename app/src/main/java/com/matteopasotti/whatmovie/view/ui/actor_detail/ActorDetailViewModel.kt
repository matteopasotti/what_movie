package com.matteopasotti.whatmovie.view.ui.actor_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matteopasotti.whatmovie.api.Result
import com.matteopasotti.whatmovie.model.ActorDetailDomainModel
import com.matteopasotti.whatmovie.usecase.GetActorDetailsUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ActorDetailViewModel(private val useCase: GetActorDetailsUseCase): ViewModel() {

    var actorId: Int = 0
    private lateinit var isLoadingLiveData: MutableLiveData<Boolean>
    private lateinit var isErrorLiveData: MutableLiveData<Boolean>

    private val _actorDetail = MutableLiveData<ActorDetailDomainModel>()
    val actorDetail: LiveData<ActorDetailDomainModel> = _actorDetail

    fun isLoading(): LiveData<Boolean> {
        if (!::isLoadingLiveData.isInitialized) {
            isLoadingLiveData = MutableLiveData()
            isLoadingLiveData.value = true
        }

        return isLoadingLiveData
    }

    fun isError(): LiveData<Boolean> {
        if (!::isErrorLiveData.isInitialized) {
            isErrorLiveData = MutableLiveData()
            isErrorLiveData.value = false
        }

        return isErrorLiveData
    }

    fun getActorDetails() {
        viewModelScope.launch {
            try {
                val actorDetailResponse = viewModelScope.async {
                    useCase.getActorDetails(actorId)
                }
                updateUI(actorDetailResponse.await())
            } catch (e: Throwable) {
                isLoadingLiveData.postValue(false)
                isErrorLiveData.postValue(true)
            }
        }
    }

    private fun updateUI(actorDetailResponse: Result<Any>) {
        when(actorDetailResponse) {
            is Result.Success -> {
                val response = actorDetailResponse.value as ActorDetailDomainModel
                isLoadingLiveData.postValue(false)
                _actorDetail.postValue(response)
            }
        }
    }
}