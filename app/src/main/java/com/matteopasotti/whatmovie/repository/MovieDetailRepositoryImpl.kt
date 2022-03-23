package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.api.MovieApiInterface
import com.matteopasotti.whatmovie.api.Result
import com.matteopasotti.whatmovie.model.MovieDetail
import com.matteopasotti.whatmovie.model.response.BasicMovieResponse
import com.matteopasotti.whatmovie.model.response.MovieCreditResponse

internal class MovieDetailRepositoryImpl(
    private val movieApi: MovieApiInterface
) : MovieDetailRepository, BaseRepository() {

    override suspend fun getRecommendedMovies(movieId: Int): Result<BasicMovieResponse> {
        return safeApiCall(
            apiCall = {
                movieApi.getRecommendedMovies(movieId, 1)
            }
        )
    }

    override suspend fun getSimilarMovies(movieId: Int): Result<BasicMovieResponse> {
        return safeApiCall(
            apiCall = {
                movieApi.getSimilarMovies(movieId, 1)
            }
        )
    }

    override suspend fun getMovieCredits(movieId: Int): Result<MovieCreditResponse> {
        return safeApiCall(
            apiCall = {
                movieApi.getMovieCredits(movieId)
            }
        )
    }

    override suspend fun getMovieDetail(movieId: Int): Result<MovieDetail> {
        return safeApiCall(
            apiCall = {
                movieApi.getMovieDetail(movieId)
            }
        )
    }


//    @Throws(IOException::class)
//    override suspend fun getSimilarMovies(movieId: Int): List<MovieDomainModel>? {
//        val response = withContext(Dispatchers.IO) { movieApi.getSimilarMovies(movieId, 1) }
//        if(response.isSuccessful){
//            return response.body()?.results?.map { it.toDomainModel() }
//        }
//        return null
//    }

//    @Throws(IOException::class)
//    override suspend fun getMovieCredits(movieId: Int): List<ActorDomainModel>? {
//        val response = withContext(Dispatchers.IO) { movieApi.getMovieCredits(movieId) }
//
//        if (response.isSuccessful) {
//            return response.body()?.cast?.filter { it.profile_path != null }?.map { it.toDomainModel() }
//        }
//
//        return null
//    }

//    @Throws(IOException::class)
//    override suspend fun getMovieDetail(movieId: Int): MovieDetailDomainModel? {
//        val response = withContext(Dispatchers.IO) { movieApi.getMovieDetail(movieId) }
//
//        if(response.isSuccessful){
//            return response.body()?.toDomainModel()
//        }
//
//        return null
//    }

}