package com.matteopasotti.whatmovie.model.response

import com.matteopasotti.whatmovie.model.Movie

data class BasicMovieResponse (
    val page: Int,
    val results: List<Movie>?)