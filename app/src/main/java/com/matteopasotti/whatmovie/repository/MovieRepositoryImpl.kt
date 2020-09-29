package com.matteopasotti.whatmovie.repository

import androidx.annotation.VisibleForTesting
import com.matteopasotti.whatmovie.BuildConfig
import com.matteopasotti.whatmovie.api.MovieApiInterface
import com.matteopasotti.whatmovie.api.Result
import com.matteopasotti.whatmovie.db.MovieDao
import com.matteopasotti.whatmovie.model.Movie
import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.model.toDomainModel
import com.matteopasotti.whatmovie.util.Utils

internal class MovieRepositoryImpl(
    private val movieApi: MovieApiInterface,
    private val movieDao: MovieDao,
    private val syncRepository: SyncRepoImpl
) : MovieRepository{


    var firstAccess = true

    override suspend fun getPopularMovies(page: Int): List<MovieDomainModel>? {
        if(!syncRepository.areDataUpdated()) {
            return getPopularMoviesFromApi(page)
        } else {
            var movies: List<MovieDomainModel>?
            if(firstAccess) {
                firstAccess = false
                movies = getAllPopularMoviesFromDb()
            } else {
                movies = getPopularMoviesFromDb(page)
                movies.also {
                    if( it.isNullOrEmpty()) {
                        movies = getPopularMoviesFromApi(page)
                    }
                }
            }

            return movies
        }
    }


    override suspend fun getPopularMoviesFromApi(page: Int): List<MovieDomainModel>? {

        val response = movieApi.getPopularMovies(BuildConfig.API_KEY, "en-US", page)

        if(response.isSuccessful) {
            val results = response.body()?.results?.filter { it.poster_path != null }
            results?.let {
                    list -> list.forEach { it.page = page }
                saveMovies(list)
                return results?.map { it.toDomainModel() }
            }
        }

        return null
    }

    override suspend fun getPopularMoviesFromDb(page: Int): List<MovieDomainModel>? =
        movieDao.getMoviesByPage(page).map { it.toDomainModel() }

    override suspend fun getAllPopularMoviesFromDb(): List<MovieDomainModel>? =
        movieDao.getMovies().map { it.toDomainModel() }

    private suspend fun saveMovies(movies: List<Movie>) {
        syncRepository.saveSyncDate(Utils.getCurrentDate())
        movieDao.insertMovies(movies)
    }

}