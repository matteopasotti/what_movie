package com.matteopasotti.whatmovie.di

import com.matteopasotti.whatmovie.repository.ActorDetailRepositoryImpl
import com.matteopasotti.whatmovie.repository.MovieDetailRepositoryImpl
import com.matteopasotti.whatmovie.repository.MovieRepositoryImpl
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repoModule = module {

    single {
        MovieRepositoryImpl(
            movieApi = get(),
            movieDao = get(), ioDispatcher = get(named("IODispatcher"))
        )
    }

    single {
        MovieDetailRepositoryImpl(
            movieApi = get(),
            ioDispatcher = get(named("IODispatcher"))
        )
    }

    single { ActorDetailRepositoryImpl(movieApi = get()) }

    single(named("IODispatcher")) {
        Dispatchers.IO
    }
}