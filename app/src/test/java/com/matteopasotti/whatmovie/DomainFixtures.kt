package com.matteopasotti.whatmovie

import com.matteopasotti.whatmovie.model.ActorDomainModel
import com.matteopasotti.whatmovie.model.MovieDomainModel

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
        id, poster_path, backdrop_path, title, overview, release_date)

    internal fun getActor(
        cast_id: Int = 123,
        character: String = "abc",
        credit_id: String = "credit_id",
        gender: Int = 0,
        name: String = "name",
        profile_path: String = "image"
    ): ActorDomainModel = ActorDomainModel(name, profile_path)
}