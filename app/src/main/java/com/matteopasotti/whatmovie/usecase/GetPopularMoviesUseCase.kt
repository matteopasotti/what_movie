package com.matteopasotti.whatmovie.usecase

import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.repository.MovieRepository

class GetPopularMoviesUseCase (
    private val movieRepository: MovieRepository) {

    suspend fun execute(): List<MovieDomainModel> {
        return movieRepository.getPopularMovies()
            .filter { it.poster_path != null }
    }
}