package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.api.Result
import com.matteopasotti.whatmovie.model.Movie
import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.model.response.BasicMovieResponse

interface MovieRepository {

    suspend fun getMoviesAtTheatre(): Result<BasicMovieResponse>

    suspend fun getTrendingOfTheWeek(): Result<BasicMovieResponse>

    suspend fun getPopularMoviesFromApi(): Result<BasicMovieResponse>

    suspend fun getPopularMoviesFromDb(page: Int): List<MovieDomainModel>?

    suspend fun getAllPopularMoviesFromDb(): List<MovieDomainModel>?

    suspend fun getLastFetchedPageFromDb(): Int?
}