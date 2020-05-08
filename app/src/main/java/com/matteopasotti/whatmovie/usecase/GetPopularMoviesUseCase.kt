package com.matteopasotti.whatmovie.usecase

import com.matteopasotti.whatmovie.model.Movie
import com.matteopasotti.whatmovie.repository.MovieRepository

class GetPopularMoviesUseCase (
    private val movieRepository: MovieRepository) {

    suspend fun execute(): List<Movie> {
        return movieRepository.getPopularMovies()
    }
}