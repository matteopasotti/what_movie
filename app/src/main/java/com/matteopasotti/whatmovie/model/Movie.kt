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