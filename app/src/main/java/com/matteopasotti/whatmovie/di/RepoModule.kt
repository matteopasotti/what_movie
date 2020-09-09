package com.matteopasotti.whatmovie.di

import com.matteopasotti.whatmovie.repository.MovieDetailRepositoryImpl
import com.matteopasotti.whatmovie.repository.MovieRepositoryImpl
import com.matteopasotti.whatmovie.repository.SyncRepoImpl
import com.matteopasotti.whatmovie.util.PreferenceContractImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val repoModule = module {

    single { MovieRepositoryImpl(movieApi = get(),
        movieDao = get(), syncRepository = get()) }

    single { MovieDetailRepositoryImpl(movieApi = get()) }

    single { SyncRepoImpl(preference = PreferenceContractImpl(providePreferences(androidApplication()))) }
}