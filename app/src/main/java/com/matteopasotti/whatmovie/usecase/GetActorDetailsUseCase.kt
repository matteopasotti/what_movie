package com.matteopasotti.whatmovie.usecase

import com.matteopasotti.whatmovie.api.Result
import com.matteopasotti.whatmovie.repository.ActorDetailRepository
import java.io.IOException

class GetActorDetailsUseCase(private val actorDetailRepository: ActorDetailRepository) {

    suspend fun getActorDetails(actorId: Int): Result<Any> {
        return try {
            actorDetailRepository.getActorDetails(actorId)?.let {
                Result.Success(it)
            }?: Result.Error("No Data")
        } catch (e: IOException) {
            Result.Error("getActorDetails has failed")
        }
    }
}