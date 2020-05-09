package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.model.MovieDomainModel

interface MovieRepository {

    suspend fun getPopularMovies(): List<MovieDomainModel>
}