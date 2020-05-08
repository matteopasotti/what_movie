package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.model.Movie

interface MovieRepository {

    suspend fun getPopularMovies(): List<Movie>
}