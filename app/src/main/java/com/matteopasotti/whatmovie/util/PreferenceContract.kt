package com.matteopasotti.whatmovie.util

interface PreferenceContract {

    fun setString(key: String, value: String)

    fun getString(key: String, defValue: String?): String?
}