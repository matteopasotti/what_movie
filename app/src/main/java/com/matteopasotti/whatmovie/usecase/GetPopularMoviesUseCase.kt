package com.matteopasotti.whatmovie.usecase

import com.matteopasotti.whatmovie.api.Result
import com.matteopasotti.whatmovie.repository.MovieRepository
import java.io.IOException
import java.lang.Exception

class GetPopularMoviesUseCase (
    private val movieRepository: MovieRepository) {

    private var page: Int = 0

    suspend fun execute(): Result<Any> {
        return try {
            page++
            movieRepository.getPopularMovies(page)?.let { list ->
                Result.Success(list)
            } ?: Result.Error("No Data")
        } catch (e: Exception) {
            Result.Error("getPopularMovies error")
        }
    }
}