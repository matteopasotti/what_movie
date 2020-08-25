package com.matteopasotti.whatmovie.usecase

import com.matteopasotti.whatmovie.api.Result
import com.matteopasotti.whatmovie.repository.MovieDetailRepository
import java.io.IOException

class GetMovieDetailsUseCase(private val movieDetailRepository: MovieDetailRepository) {

    suspend fun getRecommendedMovie(movieId: Int): Result<Any> {
        return try {
            movieDetailRepository.getRecommendedMovies(movieId)?.let {
                Result.Success(it.filter { it.poster_path != null })
            } ?: Result.Error("No Data")
        } catch (e: IOException) {
            Result.Error(e.toString())
        }

    }


    suspend fun getMovieCredits(movieId: Int): Result<Any> {
        return try {
            movieDetailRepository.getMovieCredits(movieId)?.let {
                Result.Success(it)
            }  ?: Result.Error("No Data")
        } catch (e: IOException) {
            Result.Error(e.toString())
        }
    }
}