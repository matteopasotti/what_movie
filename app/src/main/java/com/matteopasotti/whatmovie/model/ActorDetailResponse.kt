package com.matteopasotti.whatmovie.model

import com.google.gson.annotations.SerializedName

data class ActorDetailResponse(
    val id: Int,
    val name: String,
    val biography: String,
    val place_of_birth: String,
    val profile_path: String,
    val birthday: String,
    val deathday: String?,
    @SerializedName("combined_credits")
    val combined_credits: MovieCredits?,
    val images: ActorImages?
)

fun ActorDetailResponse.toDomainModel(): ActorDetailDomainModel {
    var image = "http://image.tmdb.org/t/p/original${this.profile_path}"

    var actorImage: ActorImage?
    images?.let { actorImages ->
        actorImage = actorImages.profiles.maxBy { it.vote_average }
        image = "http://image.tmdb.org/t/p/original${actorImage?.file_path}"
    }

    val name = this.name.substringBefore(delimiter = " ", missingDelimiterValue = "Delimiter not found")
    val surname = this.name.substringAfter(delimiter = " ", missingDelimiterValue = "Delimiter not found")

    val moviesSorted = this.combined_credits?.cast?.sortedByDescending { it.vote_count }
    val movies : List<MovieDomainModel>? = moviesSorted?.map { it.toDomainModel() }

    val backgroundImage = movies?.first()?.backdrop_path

    return ActorDetailDomainModel(
        name,
        surname,
        biography = this.biography,
        place_of_birth = this.place_of_birth,
        actor_image = image,
        birthday = this.birthday,
        deathday = this.deathday,
        knownFor = movies,
        background_image = backgroundImage
    )
}

data class ActorDetailDomainModel(
    val name: String,
    val surname: String,
    val biography: String,
    val place_of_birth: String,
    val actor_image: String,
    val birthday: String,
    val deathday: String?,
    val knownFor: List<MovieDomainModel>?,
    val background_image: String?
)

data class MovieCredits(
    @SerializedName("cast")
    val cast: List<Movie>
)

data class ActorImages(
    val profiles: List<ActorImage>
)

data class ActorImage(
    val file_path: String,
    val vote_count: Int,
    val vote_average: Double
)