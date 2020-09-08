package com.matteopasotti.whatmovie.repository

interface DataSyncRepository {

    fun saveSyncDate(syncDate: String)

    fun lastSyncDate(): String?

    fun areDataUpdated(): Boolean
}