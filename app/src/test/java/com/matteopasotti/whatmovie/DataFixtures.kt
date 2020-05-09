package com.matteopasotti.whatmovie

import com.matteopasotti.whatmovie.model.Movie

object DataFixtures {

    internal fun getMovie(
        id: Int = 123,
        poster_path: String = "posterPath",
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
    ): Movie = Movie(id,
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
        vote_average)


}