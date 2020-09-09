package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.util.PreferenceContract
import com.matteopasotti.whatmovie.util.PreferenceManager
import com.matteopasotti.whatmovie.util.Utils

class SyncRepoImpl(private val preference: PreferenceContract): DataSyncRepository {

    override fun saveSyncDate(syncDate: String) {
        preference.setString(PreferenceManager.LAST_DATE_SYNC, syncDate)
    }

    override fun lastSyncDate(): String? = preference.getString(PreferenceManager.LAST_DATE_SYNC, null)

    override fun areDataUpdated(): Boolean {
        val syncDate: String? = lastSyncDate()
        val currentDate = getCurrentDate()

        if(syncDate != null) {
            val diff = Utils.getDifferenceBetweenDates(syncDate, currentDate)
            return diff < 6L
        }

        return false
    }

    override fun getCurrentDate(): String = Utils.getCurrentDate()
}