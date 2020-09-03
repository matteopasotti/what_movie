package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.BuildConfig
import com.matteopasotti.whatmovie.api.MovieApiInterface
import com.matteopasotti.whatmovie.model.*

internal class MovieDetailRepositoryImpl(
    private val movieApi: MovieApiInterface): MovieDetailRepository {

    override suspend fun getRecommendedMovies(movieId: Int): List<MovieDomainModel>? =
        movieApi.getRecommendedMovies(movieId, BuildConfig.API_KEY , "en-US", 1)
            .results
            ?.map { it.toDomainModel()}

    override suspend fun getMovieCredits(movieId: Int): List<ActorDomainModel>? =
        movieApi.getMovieCredits(movieId, BuildConfig.API_KEY)
            .cast
            .map { it.toDomainModel() }

    override suspend fun getMovieDetail(movieId: Int): MovieDetailDomainModel =
        movieApi.getMovieDetail(movieId, BuildConfig.API_KEY , "en-US").toDomainModel()

}