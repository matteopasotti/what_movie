package com.matteopasotti.whatmovie.di

import android.app.Application
import android.content.SharedPreferences
import com.matteopasotti.whatmovie.repository.DataSyncRepositoryImpl
import com.matteopasotti.whatmovie.repository.MovieDetailRepositoryImpl
import com.matteopasotti.whatmovie.repository.MovieRepositoryImpl
import com.matteopasotti.whatmovie.util.PreferenceManager
import com.matteopasotti.whatmovie.view.ui.HomeGalleryMoviesViewModel
import com.matteopasotti.whatmovie.view.ui.movie_detail.MovieDetailViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    single { MovieRepositoryImpl(movieApi = get(),
    movieDao = get(), dbRepository = get()) }

    single { providePreferences(androidApplication()) }

    single { PreferenceManager(preferences = get()) }

    single { DataSyncRepositoryImpl(preferenceManager = get()) }

    single { MovieDetailRepositoryImpl(movieApi = get()) }

    viewModel { HomeGalleryMoviesViewModel(getPopularMoviesUseCase = get()) }

    viewModel { MovieDetailViewModel(getMovieDetailsUseCase = get()) }
}

fun providePreferences(application: Application) : SharedPreferences {
    return android.preference.PreferenceManager.getDefaultSharedPreferences(application)
}