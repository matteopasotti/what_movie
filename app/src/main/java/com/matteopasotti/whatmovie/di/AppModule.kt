package com.matteopasotti.whatmovie.di

import com.matteopasotti.whatmovie.view.ui.HomeGalleryMoviesViewModel
import com.matteopasotti.whatmovie.view.ui.movie_detail.MovieDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    viewModel { HomeGalleryMoviesViewModel(getPopularMoviesUseCase = get()) }

    viewModel { MovieDetailViewModel(getMovieDetailsUseCase = get()) }
}