package com.matteopasotti.whatmovie.model

data class MovieDetail(
    val genres: MutableList<Genre>,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val production_countries: MutableList<ProductionCountry>,
    val release_date: String,
    val vote_average: Float
)

fun MovieDetail.toDomainModel(): MovieDetailDomainModel {

    var type: String = ""
    this.genres.forEach {
        type += if (type != "") {
            "," + it.name
        } else {
            it.name
        }
    }

    var prodCountries: String = ""
    this.production_countries.forEach {
        prodCountries += if (prodCountries != "") {
            "," + it.name
        } else {
            it.name
        }
    }

    return MovieDetailDomainModel(
        type,
        this.original_language,
        this.original_title,
        this.overview,
        prodCountries,
        this.release_date,
        this.vote_average
    )

}

data class MovieDetailDomainModel(
    val type: String,
    val language: String,
    val originalTitle: String,
    val overview: String,
    val productionCountries: String,
    val releaseDate: String,
    val vote_average: Float
)