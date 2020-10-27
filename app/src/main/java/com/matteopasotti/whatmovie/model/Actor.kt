package com.matteopasotti.whatmovie.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Actor(
    val id: Int,
    val cast_id: Int,
    val character: String,
    val credit_id: String,
    val gender: Int,
    val name: String,
    val profile_path: String?
)

fun Actor.toDomainModel(): ActorDomainModel {
    val image : String = "http://image.tmdb.org/t/p/w185${this.profile_path}"
    return ActorDomainModel(this.id ,this.name, image)
}

@Parcelize
data class ActorDomainModel(
    val id: Int,
    val name: String,
    val profileImage: String?
) : Parcelable