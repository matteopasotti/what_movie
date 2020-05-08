package com.matteopasotti.whatmovie.di

import com.matteopasotti.whatmovie.repository.MovieRepositoryImpl
import com.matteopasotti.whatmovie.view.ui.HomeGalleryMoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    single { MovieRepositoryImpl(movieApi = get()) }

    viewModel { HomeGalleryMoviesViewModel(getPopularMoviesUseCase = get()) }
}