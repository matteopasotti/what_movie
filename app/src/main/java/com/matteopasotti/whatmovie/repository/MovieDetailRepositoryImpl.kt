package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.BuildConfig
import com.matteopasotti.whatmovie.api.MovieApiInterface
import com.matteopasotti.whatmovie.model.Actor
import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.model.toDomainModel

internal class MovieDetailRepositoryImpl(
    private val movieApi: MovieApiInterface): MovieDetailRepository {

    override suspend fun getRecommendedMovies(movieId: Int): List<MovieDomainModel>? =
        movieApi.getRecommendedMovies(movieId, BuildConfig.API_KEY , "en-US", 1)
            .results
            ?.map { it.toDomainModel()}

    override suspend fun getMovieCredits(movieId: Int): List<Actor>? =
        movieApi.getMovieCredits(movieId, BuildConfig.API_KEY)
            .cast
}