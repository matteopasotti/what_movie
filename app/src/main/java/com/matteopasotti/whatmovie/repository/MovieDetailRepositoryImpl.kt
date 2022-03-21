package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.api.MovieApiInterface
import com.matteopasotti.whatmovie.model.ActorDomainModel
import com.matteopasotti.whatmovie.model.MovieDetailDomainModel
import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.model.toDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

internal class MovieDetailRepositoryImpl(
    private val movieApi: MovieApiInterface
) : MovieDetailRepository {

    @Throws(IOException::class)
    override suspend fun getRecommendedMovies(movieId: Int): List<MovieDomainModel>? {
        val response = withContext(Dispatchers.IO) { movieApi.getRecommendedMovies(movieId, 1) }
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
        val response = withContext(Dispatchers.IO) { movieApi.getSimilarMovies(movieId, 1) }
        if(response.isSuccessful){
            return response.body()?.results?.map { it.toDomainModel() }
        }
        return null
    }

    @Throws(IOException::class)
    override suspend fun getMovieCredits(movieId: Int): List<ActorDomainModel>? {
        val response = withContext(Dispatchers.IO) { movieApi.getMovieCredits(movieId) }

        if (response.isSuccessful) {
            return response.body()?.cast?.filter { it.profile_path != null }?.map { it.toDomainModel() }
        }

        return null
    }

    @Throws(IOException::class)
    override suspend fun getMovieDetail(movieId: Int): MovieDetailDomainModel? {
        val response = withContext(Dispatchers.IO) { movieApi.getMovieDetail(movieId) }

        if(response.isSuccessful){
            return response.body()?.toDomainModel()
        }

        return null
    }

}