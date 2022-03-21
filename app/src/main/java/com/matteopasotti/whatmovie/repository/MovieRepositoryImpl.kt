package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.BuildConfig
import com.matteopasotti.whatmovie.api.MovieApiInterface
import com.matteopasotti.whatmovie.api.Result
import com.matteopasotti.whatmovie.db.MovieDao
import com.matteopasotti.whatmovie.model.Movie
import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.model.toDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import java.io.IOException

internal class MovieRepositoryImpl(
    private val movieApi: MovieApiInterface,
    private val movieDao: MovieDao
) : MovieRepository, KoinComponent, BaseRepository() {


    override suspend fun getMoviesAtTheatre(): Result<Any> {
        return safeApiCall(
            call = {
                movieApi.getMoviesInCinema(
                    page = 1,
                    startDate = "2021-03-01",
                    endDate = "2021-03-30"
                )
            }
        )
    }

    override suspend fun getTrendingOfTheWeek(): Result<Any> {
        return safeApiCall(
            call = {
                movieApi.getTrendingOfTheWeek()
            }
        )
    }

    override suspend fun getPopularMoviesFromApi(): Result<Any> {
        return safeApiCall(
            call = {
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