package com.matteopasotti.whatmovie.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RoomConverters {

    var gson = Gson()

    @TypeConverter
    fun fromListIntToString( items : MutableList<Int>?) : String? {
        if(items == null) {
            return null
        }
        return gson.toJson(items)
    }

    @TypeConverter
    fun fromStringToListInt( genre: String?) : List<Int>? {

        if(genre == null) {
            return null
        }

        val obj =  object : TypeToken<List<Int>>(){}.type

        return gson.fromJson(genre, obj)
    }
}