package com.matteopasotti.whatmovie.model

internal data class PopularMovieResponse (
    val page: Int,
    val results: List<Movie>)