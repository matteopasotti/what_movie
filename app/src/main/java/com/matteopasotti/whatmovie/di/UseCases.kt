package com.matteopasotti.whatmovie.di

import com.matteopasotti.whatmovie.repository.ActorDetailRepositoryImpl
import com.matteopasotti.whatmovie.repository.MovieDetailRepositoryImpl
import com.matteopasotti.whatmovie.repository.MovieRepositoryImpl
import com.matteopasotti.whatmovie.usecase.GetActorDetailsUseCase
import com.matteopasotti.whatmovie.usecase.GetPopularMoviesUseCase
import com.matteopasotti.whatmovie.usecase.MovieDetailsUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val useCasesModule = module {

    single {
        GetPopularMoviesUseCase(
            movieRepository = MovieRepositoryImpl(
                movieApi = get(),
                movieDao = get(),
                ioDispatcher = get(named("IODispatcher"))
            )
        )
    }

    single {
        MovieDetailsUseCase(
            movieDetailRepository = MovieDetailRepositoryImpl(
                movieApi = get(),
                ioDispatcher = get(named("IODispatcher")))
            )
    }

    single { GetActorDetailsUseCase(actorDetailRepository = ActorDetailRepositoryImpl(movieApi = get())) }
}