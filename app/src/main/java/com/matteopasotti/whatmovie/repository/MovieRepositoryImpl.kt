package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.BuildConfig
import com.matteopasotti.whatmovie.api.MovieApiInterface
import com.matteopasotti.whatmovie.model.toDomainModel

internal class MovieRepositoryImpl(
    private val movieApi: MovieApiInterface) : MovieRepository, BaseRepository() {

    override suspend fun getPopularMovies(page: Int) =
        movieApi.getPopularMovies(BuildConfig.API_KEY , "en-US", page)
            .results
            ?.map { it.toDomainModel() }
}