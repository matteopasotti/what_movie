package com.matteopasotti.whatmovie.repository

import androidx.lifecycle.LiveData
import com.matteopasotti.whatmovie.BuildConfig
import com.matteopasotti.whatmovie.api.MovieApiInterface
import com.matteopasotti.whatmovie.db.MovieDao
import com.matteopasotti.whatmovie.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

open class MovieRepository(private val movieApi: MovieApiInterface, private val movieDao: MovieDao) : BaseRepository() {

    open val popularMovies: LiveData<List<Movie>> by lazy {
        movieDao.getMovies()
    }

    open suspend fun getPopMovies() {
        val result = movieApi.getPopularMovies(BuildConfig.API_KEY , "en-US", 1).await()
        withContext(Dispatchers.IO) {
            try {
                result.let {
                    it.body()?.let {
                        movieDao.insertMovies(it.results)
                    }
                }
            } catch (e: Exception) {
                throw e
            }
        }
    }

    suspend fun getPopularMovies() : MutableList<Movie>? {
        val movieResponse = safeApiCall(
            call = {
                movieApi.getPopularMovies(BuildConfig.API_KEY , "en-US", 1).await()
            },
            errorMessage = "Error fetching popular movies"
        )

        return movieResponse?.results?.toMutableList()
    }
}