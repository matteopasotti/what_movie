package com.matteopasotti.whatmovie.api

import com.matteopasotti.whatmovie.model.ActorDetailResponse
import com.matteopasotti.whatmovie.model.MovieDetail
import com.matteopasotti.whatmovie.model.response.BasicMovieResponse
import com.matteopasotti.whatmovie.model.response.MovieCreditResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.Response

internal interface MovieApiInterface {

    @GET("movie/popular")
    suspend fun getPopularMovies(): Response<BasicMovieResponse>

    @GET("discover/movie")
    suspend fun getMoviesInCinema(
        @Query("page") page: Int,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("primary_release_date.gte") startDate: String,
        @Query("primary_release_date.lte") endDate: String
    ): Response<BasicMovieResponse>

    //https://api.themoviedb.org/3/trending/all/week?api_key=d0ac984349d63f4af1bbea3359b8fdbc
    @GET("trending/all/week")
    suspend fun getTrendingOfTheWeek(): Response<BasicMovieResponse>

    @GET("movie/{movieId}/recommendations")
    suspend fun getRecommendedMovies(
        @Path("movieId") movieId: Int,
        @Query("page") page: Int
    ): Response<BasicMovieResponse>

    @GET("movie/{movieId}/similar")
    suspend fun getSimilarMovies(
        @Path("movieId") movieId: Int,
        @Query("page") page: Int
    ): Response<BasicMovieResponse>

    @GET("movie/{movieId}/credits")
    suspend fun getMovieCredits(
        @Path("movieId") movieId: Int
    ): Response<MovieCreditResponse>

    @GET("movie/{movieId}")
    suspend fun getMovieDetail(
        @Path("movieId") movieId: Int,
        @Query("append_to_response") append: String = "videos"
    ): Response<MovieDetail>

    @GET("person/{personId}")
    suspend fun getActorDetails(
        @Path("personId") personId: Int,
        @Query("append_to_response") append: String = "combined_credits,images"
    ): Response<ActorDetailResponse>

}