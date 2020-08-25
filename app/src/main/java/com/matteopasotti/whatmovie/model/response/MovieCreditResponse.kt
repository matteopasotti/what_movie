package com.matteopasotti.whatmovie.model.response

import com.matteopasotti.whatmovie.model.Actor

internal data class MovieCreditResponse(
    val id: Int,
    val cast: List<Actor>)