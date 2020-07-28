package com.matteopasotti.whatmovie.usecase

import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.repository.MovieRepository
import java.io.IOException
import java.lang.RuntimeException

class GetPopularMoviesUseCase (
    private val movieRepository: MovieRepository) {


    sealed class Result {
        data class Success(val data: List<MovieDomainModel>): Result()
        data class Error(val e: Throwable): Result()
    }

    suspend fun execute(): Result {
        return try {
            movieRepository.getPopularMovies()?.let {
                Result.Success(it.filter { it.poster_path != null })
            } ?: Result.Error(RuntimeException("No Data"))
        } catch (e: IOException) {
            Result.Error(e)
        }
    }
}