package com.matteopasotti.whatmovie

import com.matteopasotti.whatmovie.model.*

object DataFixtures {

    internal fun getMovie(
        id: Int = 123,
        poster_path: String? = "posterPath",
        adult: Boolean = false,
        overview: String = "",
        release_date: String = "",
        original_title: String = "",
        original_language: String = "",
        title: String = "Jumanji",
        genre_ids: MutableList<Int> = mutableListOf(),
        backdrop_path: String = "",
        popularity: Double = 100.2,
        vote_count: Int = 10,
        video: Boolean = false,
        vote_average: Double = 8.0,
        page: Int = 1
    ): Movie = Movie(
        id,
        poster_path,
        adult,
        overview,
        release_date,
        original_title,
        original_language,
        title,
        genre_ids,
        backdrop_path,
        popularity,
        vote_count,
        video,
        vote_average,
        page
    )

    internal fun getActor(
        id: Int = 123,
        cast_id: Int = 1,
        character: String = "character",
        credit_id: String = "credit_id",
        gender: Int = 0,
        name: String = "name",
        profile_path: String? = "path"
    ): Actor = Actor(
        id, cast_id, character, credit_id, gender, name, profile_path
    )

    internal fun getActorDetail(
        id: Int = 1,
        name: String = "Leonardo",
        biography: String = "biography",
        place_of_birth: String = "place",
        profile_path: String = "profile_path",
        birthday: String = "date",
        deathday: String? = null,
        movieCredits: MovieCredits? = null,
        images: ActorImages? = ActorImages(profiles = listOf(ActorImage(file_path = "file_path", vote_count = 5, vote_average = 3.0)))
    ): ActorDetailResponse = ActorDetailResponse(id, name, biography, place_of_birth, profile_path, birthday, deathday, movieCredits, images)

    internal fun getMovieDetailResponse(
        genres: MutableList<Genre> = mutableListOf(),
        original_language: String = "original_language",
        original_title: String = "original_title",
        overview: String = "overview",
        production_countries: MutableList<ProductionCountry> = mutableListOf(),
        release_date: String = "date",
        vote_average: Float = 2.0F
    ): MovieDetail = MovieDetail(genres, original_language, original_title, overview, production_countries, release_date, vote_average)

}