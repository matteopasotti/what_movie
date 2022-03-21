package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.api.Result
import com.matteopasotti.whatmovie.model.MovieDomainModel

interface MovieRepository {

    suspend fun getMoviesAtTheatre(): Result<Any>

    suspend fun getTrendingOfTheWeek(): Result<Any>

    suspend fun getPopularMoviesFromApi(): Result<Any>

    suspend fun getPopularMoviesFromDb(page: Int): List<MovieDomainModel>?

    suspend fun getAllPopularMoviesFromDb(): List<MovieDomainModel>?

    suspend fun getLastFetchedPageFromDb(): Int?
}