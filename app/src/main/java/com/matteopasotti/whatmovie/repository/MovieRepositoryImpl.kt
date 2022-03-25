package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.api.MovieApiInterface
import com.matteopasotti.whatmovie.api.Result
import com.matteopasotti.whatmovie.db.MovieDao
import com.matteopasotti.whatmovie.model.Movie
import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.model.response.BasicMovieResponse
import com.matteopasotti.whatmovie.model.toDomainModel
import org.koin.core.KoinComponent
import java.text.SimpleDateFormat
import java.util.*

internal class MovieRepositoryImpl(
    private val movieApi: MovieApiInterface,
    private val movieDao: MovieDao
) : MovieRepository, KoinComponent, BaseRepository() {

    override suspend fun getMoviesAtTheatre(): Result<BasicMovieResponse> {
        return safeApiCall(
            apiCall = {
                movieApi.getMoviesInCinema(
                    page = 1,
                    startDate = getStartDate(),
                    endDate = getEndDate()
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

    private fun formatDate(date: Date): String {
        val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return df.format(date)
    }

    private fun getStartDate(): String {
        val currentTime: Date = Calendar.getInstance().time
        return formatDate(currentTime)
    }

    private fun getEndDate(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, 1)
        val date = calendar.time
       return formatDate(date)
    }

}