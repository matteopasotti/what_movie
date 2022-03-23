package com.matteopasotti.whatmovie.model.response

import com.matteopasotti.whatmovie.model.Actor

data class MovieCreditResponse(
    val id: Int,
    val cast: List<Actor>)