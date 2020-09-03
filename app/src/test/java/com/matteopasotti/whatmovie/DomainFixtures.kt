package com.matteopasotti.whatmovie

import com.matteopasotti.whatmovie.model.*

object DomainFixtures {

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
        vote_average: Double = 8.0
    ): MovieDomainModel = MovieDomainModel(
        id, poster_path, backdrop_path, title, overview, release_date, vote_average, vote_count
    )

    internal fun getActorDomainModel(
        cast_id: Int = 123,
        character: String = "abc",
        credit_id: String = "credit_id",
        gender: Int = 0,
        name: String = "name",
        profile_path: String = "image"
    ): ActorDomainModel = ActorDomainModel(1, name, profile_path)

    internal fun getActor(
        id: Int = 1,
        cast_id: Int = 123,
        character: String = "abc",
        credit_id: String = "credit_id",
        gender: Int = 0,
        name: String = "name",
        profile_path: String = "image"
    ): Actor = Actor(id, cast_id, character, credit_id, gender, name, profile_path)

    internal fun getMovieDetail(
        genres: MutableList<Genre> = mutableListOf(Genre(1, "Adventure"), Genre(2, "Horror")),
        original_language: String = "en",
        original_title: String = "title",
        overview: String = "overview",
        production_countries: MutableList<ProductionCountry> = mutableListOf(
            ProductionCountry("US", "United States of America"),
            ProductionCountry("IT", "Italy")
        ),
        release_date: String = "date"
    ): MovieDetail =
        MovieDetail(genres, original_language, original_title, overview, production_countries, release_date)
}