package com.matteopasotti.whatmovie.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.matteopasotti.whatmovie.model.Movie

@Database(entities = [(Movie::class)], version = 1, exportSchema = true)
@TypeConverters(RoomConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao() : MovieDao
}