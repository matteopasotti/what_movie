package com.matteopasotti.whatmovie.di

import androidx.room.Room
import com.matteopasotti.whatmovie.db.AppDatabase
import org.koin.dsl.module

/**
 * In-Memory Room Database definition
 */
val roomTestModule = module(override = true) {
    single {
        Room.inMemoryDatabaseBuilder(get(), AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    single { get<AppDatabase>().movieDao() }
}