package com.matteopasotti.whatmovie.model

internal data class RecommendedMovieResponse (
    val page: Int,
    val results: List<Movie>?)