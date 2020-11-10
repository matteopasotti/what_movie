package com.matteopasotti.whatmovie.di

import com.matteopasotti.whatmovie.repository.ActorDetailRepositoryImpl
import com.matteopasotti.whatmovie.repository.MovieDetailRepositoryImpl
import com.matteopasotti.whatmovie.repository.MovieRepositoryImpl
import org.koin.dsl.module

val repoModule = module {

    single { MovieRepositoryImpl(movieApi = get(),
        movieDao = get()) }

    single { MovieDetailRepositoryImpl(movieApi = get()) }

    single { ActorDetailRepositoryImpl(movieApi = get()) }
}