package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.api.Result
import retrofit2.Response

open class BaseRepository{

    suspend fun <T : Any> safeApiCall(apiCall: suspend () -> Response<T>): Result<T> {
        return safeApiResult(apiCall)
    }

    private suspend fun <T: Any> safeApiResult(call: suspend ()-> Response<T>) : Result<T>{
        val response = call.invoke()
        if(response.isSuccessful) return Result.Success(response.body()!!)

        return Result.Error(response.errorBody().toString())
    }
}