package com.matteopasotti.whatmovie.util

import android.content.SharedPreferences

class PreferenceManager (private val preferences: SharedPreferences){

    companion object {
        //Keys
        @JvmStatic val LAST_DATE_SYNC = "last_date_sync"
    }

    fun setString(key: String, value: String) {
        val editor = preferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String, defValue: String?): String? {
        return preferences.getString(key, defValue)
    }
}