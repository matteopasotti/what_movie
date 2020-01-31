package com.matteopasotti.whatmovie.di

import com.matteopasotti.whatmovie.repository.MovieRepository
import com.matteopasotti.whatmovie.view.MainActivityViewModel
import com.matteopasotti.whatmovie.view.ui.HomeGalleryMoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    single { MovieRepository(movieApi = get()) }

    viewModel { MainActivityViewModel(get()) }

    viewModel { HomeGalleryMoviesViewModel(get()) }
}