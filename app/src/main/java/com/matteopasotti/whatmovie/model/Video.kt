package com.matteopasotti.whatmovie.model

data class MovieVideo (
    val results: MutableList<Video>
)

data class Video(
    val id: String,
    val key: String,
    val name: String,
    val site: String,
    val size: Int,
    val type: String
)

fun Video.toDomainModel(): VideoDomainModel {
    return VideoDomainModel(key = this.key , type = this.type, site = this.site)
}

data class VideoDomainModel(
    val key: String,
    val type: String,
    val site: String
)