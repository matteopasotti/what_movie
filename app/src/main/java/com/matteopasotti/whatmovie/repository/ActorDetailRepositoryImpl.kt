package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.BuildConfig
import com.matteopasotti.whatmovie.api.MovieApiInterface
import com.matteopasotti.whatmovie.model.ActorDetailDomainModel
import com.matteopasotti.whatmovie.model.toDomainModel

internal class ActorDetailRepositoryImpl(private val movieApi: MovieApiInterface): ActorDetailRepository {


    override suspend fun getActorDetails(actorId: Int): ActorDetailDomainModel? {
        val response = movieApi.getActorDetails(personId = actorId, apiKey = BuildConfig.API_KEY, language = "en-US")

        if(response.isSuccessful) {
            return response
                .body()?.toDomainModel()
        }

        return null
    }
}