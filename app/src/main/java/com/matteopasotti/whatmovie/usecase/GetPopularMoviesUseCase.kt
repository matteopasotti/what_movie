package com.matteopasotti.whatmovie.usecase

import com.matteopasotti.whatmovie.api.Result
import com.matteopasotti.whatmovie.repository.MovieRepository
import org.koin.core.KoinComponent
import java.io.IOException
import java.lang.Exception

class GetPopularMoviesUseCase (
    private val movieRepository: MovieRepository): KoinComponent {

    suspend fun getPopularMovies(): Result<Any> {
        return movieRepository.getPopularMoviesFromApi()
    }

    suspend fun getMoviesAtCinema(): Result<Any> {
        return  movieRepository.getMoviesAtTheatre()
    }

    suspend fun getTrendingOfTheWeek(): Result<Any> {
        return movieRepository.getTrendingOfTheWeek()
//        return try {
//            movieRepository.getTrendingOfTheWeek()?.let {
//                Result.Success(it)
//            } ?: Result.Error("getTrendingOfTheWeek error")
//        } catch (e: Exception) {
//            Result.Error("getTrendingOfTheWeek error")
//        }
    }
}