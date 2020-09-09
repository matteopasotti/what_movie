package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.util.PreferenceContract
import com.matteopasotti.whatmovie.util.PreferenceManager
import com.matteopasotti.whatmovie.util.Utils
import com.nhaarman.mockitokotlin2.given
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SyncRepoImplTest {

    @Mock
    internal lateinit var preference: PreferenceContract

    private lateinit var repository: SyncRepoImpl

    @Before
    fun setUp() {
        repository = SyncRepoImpl(preference)
    }

    @Test
    fun `Given the difference between lastDateSync and currentDate is 6 days or more, Then areDataUpdated returns false`(){
        given (preference.getString(PreferenceManager.LAST_DATE_SYNC, null)).willReturn("1/08/2020")

        val result = repository.areDataUpdated()

        assertFalse(result)
    }

    @Test
    fun `Given the difference between lastDateSync and currentDate is 0, Then areDataUpdated returns true`() {
        given (preference.getString(PreferenceManager.LAST_DATE_SYNC, null)).willReturn(Utils.getCurrentDate())

        val result = repository.areDataUpdated()

        assertTrue(result)
    }
}