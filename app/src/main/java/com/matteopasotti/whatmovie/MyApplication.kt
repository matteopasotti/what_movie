package com.matteopasotti.whatmovie

import android.app.Application
import com.matteopasotti.whatmovie.di.dbModule
import com.matteopasotti.whatmovie.di.mainModule
import com.matteopasotti.whatmovie.di.netModule
import com.matteopasotti.whatmovie.di.useCasesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // start Koin context
        startKoin {
            //inject Android context
            androidContext(this@MyApplication)
            // Koin Android logger
            androidLogger()
            // use properties from assets/koin.properties
            androidFileProperties()
            modules(listOf(mainModule, netModule, dbModule, useCasesModule))
        }
    }
}