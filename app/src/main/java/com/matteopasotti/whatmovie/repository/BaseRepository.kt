package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.api.Result
import retrofit2.Response

open class BaseRepository{

    suspend fun <T : Any> safeApiCall(apiCall: suspend () -> T): Result<T> {
        return try {
            Result.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            Result.Error("Api Call Failed")
        }
    }
}