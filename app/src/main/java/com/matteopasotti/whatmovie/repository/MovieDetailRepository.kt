package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.model.MovieDomainModel

interface MovieDetailRepository {

    suspend fun getRecommendedMovies(movieId: Int): List<MovieDomainModel>?
}