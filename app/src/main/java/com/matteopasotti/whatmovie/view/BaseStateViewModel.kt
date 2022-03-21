package com.matteopasotti.whatmovie.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseStateViewModel<State, Event>(
    private val initialState: State
) : ViewModel() {

    private val _viewState = MutableLiveData(initialState)
    val viewState: LiveData<State> = _viewState

    val viewEvents = SingleLiveEvent<Event>()

    val currentViewState: State
        get() {
            return _viewState.value ?: initialState
        }

    fun setState(state: State) {
        _viewState.postValue(state)
    }

    fun emitEvent(event: Event) {
        event?.let { viewEvents.postValue(it) }
    }
}