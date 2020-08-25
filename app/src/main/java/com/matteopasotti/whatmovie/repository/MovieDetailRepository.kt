package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.model.Actor
import com.matteopasotti.whatmovie.model.MovieDomainModel

interface MovieDetailRepository {

    suspend fun getRecommendedMovies(movieId: Int): List<MovieDomainModel>?

    suspend fun getMovieCredits(movieId: Int): List<Actor>?
}