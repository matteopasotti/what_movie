package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.BuildConfig
import com.matteopasotti.whatmovie.api.MovieApiInterface
import com.matteopasotti.whatmovie.db.MovieDao
import com.matteopasotti.whatmovie.model.Movie
import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.model.toDomainModel
import com.matteopasotti.whatmovie.util.Utils

internal class MovieRepositoryImpl(
    private val movieApi: MovieApiInterface,
    private val movieDao: MovieDao,
    private val syncRepository: SyncRepoImpl
) : MovieRepository, BaseRepository() {

    override suspend fun getPopularMovies(page: Int) =
        if(!syncRepository.areDataUpdated()) {
            getPopularMoviesFromApi(page)
        } else {
            getPopularMoviesFromDb()
        }

    override suspend fun getPopularMoviesFromApi(page: Int): List<MovieDomainModel>? {
        val movies = movieApi.getPopularMovies(BuildConfig.API_KEY, "en-US", page).results

        movies?.let {
            saveMovies(it)
        }

        return movies?.map { it.toDomainModel() }
    }

    override suspend fun getPopularMoviesFromDb(): List<MovieDomainModel>? =
        movieDao.getMovies().map { it.toDomainModel() }

    private suspend fun saveMovies(movies: List<Movie>) {
        syncRepository.saveSyncDate(Utils.getCurrentDate())
        movieDao.insertMovies(movies)
    }

}