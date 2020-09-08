package com.matteopasotti.whatmovie.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.matteopasotti.whatmovie.model.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    @Query("SELECT * FROM Movie WHERE Movie.id = :id")
    fun getMovieById( id : Int) : LiveData<Movie>

    @Query("SELECT * FROM Movie")
    suspend fun getMovies() : List<Movie>

    @Query("DELETE FROM Movie WHERE id =:id")
    fun deleteMovieById(id: Int)

    @Query("DELETE FROM Movie")
    fun deleteMovies()
}