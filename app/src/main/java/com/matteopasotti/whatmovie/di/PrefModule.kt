package com.matteopasotti.whatmovie.di

import android.app.Application
import android.content.SharedPreferences
import com.matteopasotti.whatmovie.util.PreferenceContractImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val prefModule = module {
    single { PreferenceContractImpl(preferences = providePreferences(androidApplication())) }
}

fun providePreferences(application: Application) : SharedPreferences {
    return android.preference.PreferenceManager.getDefaultSharedPreferences(application)
}