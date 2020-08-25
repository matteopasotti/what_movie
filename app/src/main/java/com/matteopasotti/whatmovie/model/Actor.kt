package com.matteopasotti.whatmovie.model

data class Actor(
    val cast_id: Int,
    val character: String,
    val credit_id: String,
    val gender: Int,
    val name: String,
    val profile_path: String
)