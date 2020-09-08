package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.util.PreferenceManager
import com.matteopasotti.whatmovie.util.Utils

class DataSyncRepositoryImpl(private val preferenceManager: PreferenceManager): DataSyncRepository {


    override fun saveSyncDate(syncDate: String) {
        preferenceManager.setString(PreferenceManager.LAST_DATE_SYNC, syncDate)
    }

    override fun lastSyncDate(): String? = preferenceManager.getString(PreferenceManager.LAST_DATE_SYNC, null)

    override fun areDataUpdated(): Boolean {
        val syncDate: String? = lastSyncDate()

        if(syncDate != null) {
            val diff = Utils.getDifferenceBetweenDates(syncDate, Utils.getCurrentDate())
            return diff < 6L
        }

        return false
    }
}