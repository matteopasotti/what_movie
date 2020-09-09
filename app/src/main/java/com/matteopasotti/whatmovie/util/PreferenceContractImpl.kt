package com.matteopasotti.whatmovie.util

import android.content.SharedPreferences

class PreferenceContractImpl(private val preferences: SharedPreferences): PreferenceContract {

    companion object {
        //Keys
        @JvmStatic val LAST_DATE_SYNC = "last_date_sync"
    }

    override fun setString(key: String, value: String) {
        val editor = preferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    override fun getString(key: String, defValue: String?): String? {
        return preferences.getString(key, defValue)
    }
}