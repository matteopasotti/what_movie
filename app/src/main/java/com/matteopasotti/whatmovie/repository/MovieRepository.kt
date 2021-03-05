package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.model.MovieDomainModel

interface MovieRepository {

    suspend fun getPopularMovies(page: Int): List<MovieDomainModel>?

    suspend fun getMoviesAtTheatre(): List<MovieDomainModel>?

    suspend fun getPopularMoviesFromApi(page: Int): List<MovieDomainModel>?

    suspend fun getPopularMoviesFromDb(page: Int): List<MovieDomainModel>?

    suspend fun getAllPopularMoviesFromDb(): List<MovieDomainModel>?

    suspend fun getLastFetchedPageFromDb(): Int?
}