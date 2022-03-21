package com.matteopasotti.whatmovie.model

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
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
    val original_name: String?,
    val original_language: String?,
    val title: String?,
    val genre_ids: MutableList<Int>,
    val backdrop_path: String?,
    val popularity: Double,
    val vote_count: Int,
    val video: Boolean,
    val vote_average: Double,
    val media_type: String,
    var page: Int?
) : Parcelable, Comparable<Movie> {

    override fun compareTo(other: Movie): Int {
        if (this.popularity > other.popularity) {
            return 1
        } else if (this.popularity < other.popularity) {
            return -1
        }

        return 0
    }

}

fun Movie.toDomainModel(): MovieDomainModel {

    var imagePosterPath: String? = null
    var carouselUrl: String? = null
    if (this.poster_path != null) {
        imagePosterPath = "https://image.tmdb.org/t/p/w342$poster_path"
    }


    var imageBackdropPath: String? = null
    if (this.backdrop_path != null) {
        imageBackdropPath = "https://image.tmdb.org/t/p/w1280$backdrop_path"
        carouselUrl = imageBackdropPath

    }

    var title : String? = if(this.original_name != null && this.media_type == "tv") {
        this.original_name
    } else {
        this.original_title
    }

    return MovieDomainModel(
        id = this.id,
        poster_path = imagePosterPath,
        backdrop_path = imageBackdropPath,
        title = title,
        overview = this.overview,
        release_date = this.release_date,
        vote_average = this.vote_average,
        vote_count = this.vote_count,
        imgUrl = carouselUrl,
        carouselItemTitle = title
    )
}

@Parcelize
data class MovieDomainModel(
    override val id: Int,
    val poster_path: String?,
    val backdrop_path: String?,
    val title: String?,
    val overview: String?,
    val release_date: String?,
    val vote_average: Double,
    val vote_count: Int,
    override val imgUrl: String?,
    override val carouselItemTitle: String?
) : Parcelable, CarouselEntity()