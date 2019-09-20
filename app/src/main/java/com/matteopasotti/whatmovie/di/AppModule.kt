package com.matteopasotti.whatmovie.di

import com.matteopasotti.whatmovie.repository.MovieRepository
import com.matteopasotti.whatmovie.view.MainActivityViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    single { MovieRepository(movieApi = get()) }

    viewModel { MainActivityViewModel(get()) }
}