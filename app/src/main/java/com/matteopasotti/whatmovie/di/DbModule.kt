package com.matteopasotti.whatmovie.di

import androidx.room.Room
import com.matteopasotti.whatmovie.db.AppDatabase
import org.koin.dsl.module

val dbModule = module {

    single { Room.databaseBuilder(get(), AppDatabase::class.java, "WhatMovie.db")
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build() }

    single { get<AppDatabase>().movieDao() }

}