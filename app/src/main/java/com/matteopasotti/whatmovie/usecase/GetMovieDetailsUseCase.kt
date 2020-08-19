package com.matteopasotti.whatmovie.usecase

import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.repository.MovieDetailRepository
import java.io.IOException
import java.lang.RuntimeException

class GetMovieDetailsUseCase(private val movieDetailRepository: MovieDetailRepository) {

    sealed class Result {
        data class Success(val data: List<MovieDomainModel>): Result()
        data class Error(val e: Throwable): Result()
    }

    suspend fun getRecommendedMovie(movieId: Int): Result {
        return try {
            movieDetailRepository.getRecommendedMovies(movieId)?.let {
                Result.Success(it.filter { it.poster_path != null })
            } ?: Result.Error(RuntimeException("No Data"))
        } catch (e: IOException) {
            Result.Error(e)
        }

    }
}