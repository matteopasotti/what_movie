package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.BuildConfig
import com.matteopasotti.whatmovie.api.MovieApiInterface
import com.matteopasotti.whatmovie.model.Movie


interface MovieRepository {
    suspend fun getPopularMovies() : MutableList<Movie>?
}


class MovieRepositoryImpl(val movieApi: MovieApiInterface) : BaseRepository(), MovieRepository {

    override suspend fun getPopularMovies(): MutableList<Movie>? {
        val movieResponse = safeApiCall(
            call = { movieApi.getPopularMovies(BuildConfig.API_KEY , "en-US", 1) },
            errorMessage = "Error fetching popular movies"
        )

        return movieResponse?.results?.toMutableList()
    }

}