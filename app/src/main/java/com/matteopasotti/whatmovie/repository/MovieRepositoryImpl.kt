package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.BuildConfig
import com.matteopasotti.whatmovie.api.MovieApiInterface
import com.matteopasotti.whatmovie.model.toDomainModel

internal class MovieRepositoryImpl(
    private val movieApi: MovieApiInterface) : MovieRepository {

    override suspend fun getPopularMovies() =
        movieApi.getPopularMovies(BuildConfig.API_KEY , "en-US", 1)
            .results
            .map { it.toDomainModel() }
}