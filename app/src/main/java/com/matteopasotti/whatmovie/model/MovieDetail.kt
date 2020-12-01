package com.matteopasotti.whatmovie.model

data class MovieDetail(
    val genres: MutableList<Genre>,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val production_countries: MutableList<ProductionCountry>,
    val release_date: String,
    val vote_average: Float,
    val videos: MovieVideo,
    val runtime: Int
)

fun MovieDetail.toDomainModel(): MovieDetailDomainModel {

    var type = ""
    this.genres.forEach {
        type += if (type != "") {
            "," + it.name
        } else {
            it.name
        }
    }

    var prodCountries = ""
    this.production_countries.forEach {
        prodCountries += if (prodCountries != "") {
            "," + it.name
        } else {
            it.name
        }
    }

    val genres: List<Genre> = if (this.genres.size > 3) {
        this.genres.take(3)
    } else {
        this.genres
    }


    return MovieDetailDomainModel(
        type,
        this.original_language,
        this.original_title,
        this.overview,
        prodCountries,
        this.release_date,
        this.vote_average,
        genres,
        this.videos.results.map { it.toDomainModel() },
        getDuration(this.runtime)
    )

}

fun getDuration(runtime: Int): String {
    val hours : Int = runtime / 60
    val minutes = runtime - (hours * 60)
    return """${hours}h ${minutes}m"""
}

data class MovieDetailDomainModel(
    val type: String,
    val language: String,
    val originalTitle: String,
    val overview: String,
    val productionCountries: String,
    val releaseDate: String,
    val vote_average: Float,
    val genres: List<Genre>,
    val videos: List<VideoDomainModel>,
    val duration: String
)