package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.api.MovieApiInterface
import com.matteopasotti.whatmovie.api.Result
import com.matteopasotti.whatmovie.model.MovieDetail
import com.matteopasotti.whatmovie.model.response.BasicMovieResponse
import com.matteopasotti.whatmovie.model.response.MovieCreditResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class MovieDetailRepositoryImpl(
    private val movieApi: MovieApiInterface,
    private val ioDispatcher: CoroutineDispatcher
) : MovieDetailRepository, BaseRepository() {

    override suspend fun getRecommendedMovies(movieId: Int): Result<BasicMovieResponse> {
        return withContext(ioDispatcher) {
            safeApiCall(
                apiCall = {
                    movieApi.getRecommendedMovies(movieId, 1)
                }
            )
        }

    }

    override suspend fun getSimilarMovies(movieId: Int): Result<BasicMovieResponse> {
        return withContext(ioDispatcher) {
            safeApiCall(
                apiCall = {
                    movieApi.getSimilarMovies(movieId, 1)
                }
            )
        }
    }

    override suspend fun getMovieCredits(movieId: Int): Result<MovieCreditResponse> {
        return withContext(ioDispatcher) {
            safeApiCall(
                apiCall = {
                    movieApi.getMovieCredits(movieId)
                }
            )
        }
    }

    override suspend fun getMovieDetail(movieId: Int): Result<MovieDetail> {
        return withContext(ioDispatcher) {
            safeApiCall(
                apiCall = {
                    movieApi.getMovieDetail(movieId)
                }
            )
        }
    }

}