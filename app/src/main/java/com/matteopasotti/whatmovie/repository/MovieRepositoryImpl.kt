package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.BuildConfig
import com.matteopasotti.whatmovie.api.MovieApiInterface
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
) : MovieRepository, KoinComponent {

    @Throws(IOException::class)
    override suspend fun getPopularMovies(page: Int): List<MovieDomainModel>? {

        val pageFromDb = getLastFetchedPageFromDb()

        return if (pageFromDb != null) {
            if (page > pageFromDb) {
                //fetch from api
                getPopularMoviesFromApi(page)
            } else {
                //fetch from db
                getPopularMoviesFromDb(page)
            }
        } else {
            return getPopularMoviesFromApi(page)
        }
    }

    override suspend fun getMoviesAtTheatre(): List<MovieDomainModel>? {
        val response = movieApi.getMoviesInCinema(
            page = 1,
            startDate = "2021-03-01",
            endDate = "2021-03-30"
        )


        if (response.isSuccessful) {
            response.body()?.results?.let { movies ->
                return movies.map { it.toDomainModel() }
            }
        }

        return null
    }


    @Throws(IOException::class)
    override suspend fun getPopularMoviesFromApi(page: Int): List<MovieDomainModel>? {

        val response = movieApi.getPopularMovies(
                BuildConfig.API_KEY,
                "en-US",
                page
            )

        if (response.isSuccessful) {
            val results = response.body()?.results?.filter { it.poster_path != null }
            results?.let { list ->
                list.forEach { it.page = page }
                withContext(Dispatchers.IO) {
                    saveMovies(list)
                }

                return results.map { it.toDomainModel() }
            }
        }

        return null
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