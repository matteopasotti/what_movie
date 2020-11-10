package com.matteopasotti.whatmovie

import com.matteopasotti.whatmovie.model.*

object DomainFixtures {

    internal fun getMovie(
        id: Int = 123,
        poster_path: String? = "posterPath",
        overview: String = "",
        release_date: String = "",
        title: String = "Jumanji",
        backdrop_path: String = "",
        vote_count: Int = 10,
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

    internal fun getActorDetailDomainModel(
        name: String = "",
        surname: String = "",
        biography: String = "",
        place_of_birth: String = "",
        actor_image: String = "",
        birthday: String = "",
        deathday: String? = "",
        knownFor: List<MovieDomainModel>? = null
    ): ActorDetailDomainModel = ActorDetailDomainModel(name, surname, biography, place_of_birth, actor_image, birthday, deathday, knownFor)

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

    internal fun getMovieDetail(
        genres: MutableList<Genre> = mutableListOf(Genre(1, "Adventure"), Genre(2, "Horror")),
        original_language: String = "en",
        original_title: String = "title",
        overview: String = "overview",
        production_countries: MutableList<ProductionCountry> = mutableListOf(
            ProductionCountry("US", "United States of America"),
            ProductionCountry("IT", "Italy")
        ),
        release_date: String = "date",
        vote_average: Float = 2.0F,
        videos: MovieVideo = MovieVideo(mutableListOf())
    ): MovieDetail =
        MovieDetail(genres, original_language, original_title, overview, production_countries, release_date, vote_average, videos)
}