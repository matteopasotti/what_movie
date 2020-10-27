package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.model.ActorDetailDomainModel

interface ActorDetailRepository {

    suspend fun getActorDetails(actorId: Int): ActorDetailDomainModel?
}