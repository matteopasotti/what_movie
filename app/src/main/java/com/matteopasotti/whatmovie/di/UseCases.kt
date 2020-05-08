package com.matteopasotti.whatmovie.di

import com.matteopasotti.whatmovie.repository.MovieRepositoryImpl
import com.matteopasotti.whatmovie.usecase.GetPopularMoviesUseCase
import org.koin.dsl.module

val useCasesModule = module {
    single { GetPopularMoviesUseCase(movieRepository = MovieRepositoryImpl(get())) }
}