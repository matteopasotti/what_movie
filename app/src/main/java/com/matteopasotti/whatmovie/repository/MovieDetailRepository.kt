package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.api.Result
import com.matteopasotti.whatmovie.model.MovieDetail
import com.matteopasotti.whatmovie.model.response.BasicMovieResponse
import com.matteopasotti.whatmovie.model.response.MovieCreditResponse

interface MovieDetailRepository {

    suspend fun getRecommendedMovies(movieId: Int): Result<BasicMovieResponse>

    suspend fun getSimilarMovies(movieId: Int): Result<BasicMovieResponse>

    suspend fun getMovieCredits(movieId: Int): Result<MovieCreditResponse>

    suspend fun getMovieDetail(movieId: Int): Result<MovieDetail>
}