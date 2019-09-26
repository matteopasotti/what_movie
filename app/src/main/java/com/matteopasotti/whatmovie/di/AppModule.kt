package com.matteopasotti.whatmovie.di

import com.matteopasotti.whatmovie.repository.MovieRepositoryImpl
import com.matteopasotti.whatmovie.view.MainActivityViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    single { MovieRepositoryImpl(movieApi = get()) }

    viewModel { MainActivityViewModel(get()) }
}