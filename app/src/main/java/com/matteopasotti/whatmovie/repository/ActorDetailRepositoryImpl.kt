package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.BuildConfig
import com.matteopasotti.whatmovie.api.MovieApiInterface
import com.matteopasotti.whatmovie.model.ActorDetailDomainModel
import com.matteopasotti.whatmovie.model.toDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class ActorDetailRepositoryImpl(private val movieApi: MovieApiInterface): ActorDetailRepository {


    override suspend fun getActorDetails(actorId: Int): ActorDetailDomainModel? {
        val response = withContext(Dispatchers.IO) { movieApi.getActorDetails(personId = actorId) }

        if(response.isSuccessful) {
            return response
                .body()?.toDomainModel()
        }

        return null
    }
}