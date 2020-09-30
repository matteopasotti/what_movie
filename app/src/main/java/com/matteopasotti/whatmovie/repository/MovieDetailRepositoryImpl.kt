package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.BuildConfig
import com.matteopasotti.whatmovie.api.MovieApiInterface
import com.matteopasotti.whatmovie.model.*
import java.io.IOException

internal class MovieDetailRepositoryImpl(
    private val movieApi: MovieApiInterface
) : MovieDetailRepository {

    @Throws(IOException::class)
    override suspend fun getRecommendedMovies(movieId: Int): List<MovieDomainModel>? {
        val response = movieApi.getRecommendedMovies(movieId, BuildConfig.API_KEY, "en-US", 1)
        if (response.isSuccessful) {
            return response
                .body()
                ?.results
                ?.map { it.toDomainModel() }
        }

        return null
    }


    @Throws(IOException::class)
    override suspend fun getSimilarMovies(movieId: Int): List<MovieDomainModel>? {
        val response = movieApi.getSimilarMovies(movieId, BuildConfig.API_KEY, "en-US", 1)
        if(response.isSuccessful){
            return response.body()?.results?.map { it.toDomainModel() }
        }
        return null
    }

    @Throws(IOException::class)
    override suspend fun getMovieCredits(movieId: Int): List<ActorDomainModel>? {
        val response =  movieApi.getMovieCredits(movieId, BuildConfig.API_KEY)
        if (response.isSuccessful) {
            return response.body()?.cast?.filter { it.profile_path != null }?.map { it.toDomainModel() }
        }

        return null
    }

    @Throws(IOException::class)
    override suspend fun getMovieDetail(movieId: Int): MovieDetailDomainModel? {
        val response = movieApi.getMovieDetail(movieId, BuildConfig.API_KEY, "en-US")
        if(response.isSuccessful){
            return response.body()?.toDomainModel()
        }

        return null
    }

}