package com.matteopasotti.whatmovie.api

import com.matteopasotti.whatmovie.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface MovieApiInterface {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : MovieResponse

}