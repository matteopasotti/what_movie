package com.matteopasotti.whatmovie.api

sealed class Result<out T> {
    data class Success<out T>(val value: T): Result<T>()
    data class Error(val errorMessage: String) : Result<Nothing>()
}