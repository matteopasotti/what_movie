package com.matteopasotti.whatmovie.di

import com.matteopasotti.whatmovie.view.ui.home.HomeGalleryMoviesViewModel
import com.matteopasotti.whatmovie.view.ui.actor_detail.ActorDetailViewModel
import com.matteopasotti.whatmovie.view.ui.movie_detail.MovieDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    viewModel {
        HomeGalleryMoviesViewModel(
            useCase = get()
        )
    }

    viewModel { MovieDetailViewModel(movieDetailsUseCase = get()) }

    viewModel { ActorDetailViewModel(get()) }
}