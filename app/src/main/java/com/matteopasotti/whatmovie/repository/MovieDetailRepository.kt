package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.model.ActorDomainModel
import com.matteopasotti.whatmovie.model.MovieDetail
import com.matteopasotti.whatmovie.model.MovieDetailDomainModel
import com.matteopasotti.whatmovie.model.MovieDomainModel

interface MovieDetailRepository {

    suspend fun getRecommendedMovies(movieId: Int): List<MovieDomainModel>?

    suspend fun getSimilarMovies(movieId: Int): List<MovieDomainModel>?

    suspend fun getMovieCredits(movieId: Int): List<ActorDomainModel>?

    suspend fun getMovieDetail(movieId: Int): MovieDetailDomainModel
}