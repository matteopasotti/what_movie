package com.matteopasotti.whatmovie.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Entity(primaryKeys = ["id"])
@Parcelize
data class Movie(
    val id: Int,
    val poster_path: String?,
    val adult: Boolean,
    val overview: String?,
    val release_date: String?,
    val original_title: String?,
    val original_language: String?,
    val title: String?,
    val genre_ids: MutableList<Int>,
    val backdrop_path: String?,
    val popularity: Double,
    val vote_count: Int,
    val video: Boolean,
    val vote_average: Double
) : Parcelable

fun Movie.toDomainModel(): MovieDomainModel {

    var image : String? = null
    if(this.poster_path != null) {
        image = "http://image.tmdb.org/t/p/w185$poster_path"

    }
    return MovieDomainModel(
        id = this.id,
        poster_path = image,
        title = this.title
    )
}

data class MovieDomainModel(
    val id: Int,
    val poster_path: String?,
    val title: String?
)