package com.matteopasotti.whatmovie.usecase

import com.matteopasotti.whatmovie.api.Result
import com.matteopasotti.whatmovie.repository.MovieRepository
import java.io.IOException

class GetPopularMoviesUseCase (
    private val movieRepository: MovieRepository) {

    private var page: Int = 0

    suspend fun execute(): Result<Any> {
        return try {
            page++
            movieRepository.getPopularMovies(page)?.let { list ->
                Result.Success(list.filter { it.poster_path != null })
            } ?: Result.Error("No Data")
        } catch (e: IOException) {
            Result.Error("getPopularMovies error")
        }
    }
}