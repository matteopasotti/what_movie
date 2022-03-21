package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.api.MovieApiInterface
import com.matteopasotti.whatmovie.api.Result
import com.matteopasotti.whatmovie.db.MovieDao
import com.matteopasotti.whatmovie.model.Movie
import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.model.response.BasicMovieResponse
import com.matteopasotti.whatmovie.model.toDomainModel
import org.koin.core.KoinComponent

internal class MovieRepositoryImpl(
    private val movieApi: MovieApiInterface,
    private val movieDao: MovieDao
) : MovieRepository, KoinComponent, BaseRepository() {


    override suspend fun getMoviesAtTheatre(): Result<BasicMovieResponse> {
        return safeApiCall(
            apiCall = {
                movieApi.getMoviesInCinema(
                    page = 1,
                    startDate = "2021-03-01",
                    endDate = "2021-03-30"
                )
            }
        )
    }

    override suspend fun getTrendingOfTheWeek(): Result<BasicMovieResponse> {
        return safeApiCall(
            apiCall = {
                movieApi.getTrendingOfTheWeek()
            }
        )
    }

    override suspend fun getPopularMoviesFromApi(): Result<BasicMovieResponse> {
        return safeApiCall(
            apiCall = {
                movieApi.getPopularMovies()
            }
        )
    }

    override suspend fun getPopularMoviesFromDb(page: Int): List<MovieDomainModel>? =
        movieDao.getMoviesByPage(page).map { it.toDomainModel() }

    override suspend fun getAllPopularMoviesFromDb(): List<MovieDomainModel>? =
        movieDao.getMovies().map { it.toDomainModel() }

    override suspend fun getLastFetchedPageFromDb(): Int? {
        return movieDao.getLastPageFetched()
    }

    private suspend fun saveMovies(movies: List<Movie>) {
        movieDao.insertMovies(movies)
    }

}