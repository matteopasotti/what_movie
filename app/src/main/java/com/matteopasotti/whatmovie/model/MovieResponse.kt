package com.matteopasotti.whatmovie.model

internal data class MovieResponse (
    val page: Int,
    val results: List<Movie>)