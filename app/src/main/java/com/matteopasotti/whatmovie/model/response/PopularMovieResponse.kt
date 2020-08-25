package com.matteopasotti.whatmovie.model.response

import com.matteopasotti.whatmovie.model.Movie

internal data class PopularMovieResponse (
    val page: Int,
    val results: List<Movie>?)