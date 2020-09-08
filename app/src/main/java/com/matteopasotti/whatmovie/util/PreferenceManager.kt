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


    fun setInt(key: String, value: Int) {
        val editor = preferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getInt(key: String, defValue: Int): Int {
        return preferences.getInt(key, defValue)
    }


    fun setBoolean(key: String, value: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return preferences.getBoolean(key, defValue)
    }
}