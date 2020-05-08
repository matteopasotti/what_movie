package com.matteopasotti.whatmovie.repository

import androidx.lifecycle.LiveData
import com.matteopasotti.whatmovie.BuildConfig
import com.matteopasotti.whatmovie.api.MovieApiInterface
import com.matteopasotti.whatmovie.db.MovieDao
import com.matteopasotti.whatmovie.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

internal class MovieRepositoryImpl(
    private val movieApi: MovieApiInterface) : BaseRepository(), MovieRepository {

    override suspend fun getPopularMovies() =
        movieApi.getPopularMovies(BuildConfig.API_KEY , "en-US", 1)
            .results
}