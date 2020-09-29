package com.matteopasotti.whatmovie.api

import com.matteopasotti.whatmovie.model.MovieDetail
import com.matteopasotti.whatmovie.model.response.MovieCreditResponse
import com.matteopasotti.whatmovie.model.response.PopularMovieResponse
import com.matteopasotti.whatmovie.model.response.BasicMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface MovieApiInterface {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : Response<PopularMovieResponse>

    @GET("movie/{movieId}/recommendations")
    suspend fun getRecommendedMovies(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : Response<BasicMovieResponse>

    @GET("movie/{movieId}/similar")
    suspend fun getSimilarMovies(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<BasicMovieResponse>

    @GET("movie/{movieId}/credits")
    suspend fun getMovieCredits(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String
    ) : Response<MovieCreditResponse>

    @GET("movie/{movieId}")
    suspend fun getMovieDetail(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ) : Response<MovieDetail>

}