package com.matteopasotti.whatmovie.di

import com.matteopasotti.whatmovie.repository.DataSyncRepositoryImpl
import com.matteopasotti.whatmovie.repository.MovieDetailRepositoryImpl
import com.matteopasotti.whatmovie.repository.MovieRepositoryImpl
import com.matteopasotti.whatmovie.usecase.GetMovieDetailsUseCase
import com.matteopasotti.whatmovie.usecase.GetPopularMoviesUseCase
import org.koin.dsl.module

val useCasesModule = module {

    single {
        GetPopularMoviesUseCase(
            movieRepository = MovieRepositoryImpl(
                movieApi = get(),
                movieDao = get(),
                dbRepository = DataSyncRepositoryImpl(preferenceManager = get())
            )
        )
    }

    single { GetMovieDetailsUseCase(movieDetailRepository = MovieDetailRepositoryImpl(get())) }
}