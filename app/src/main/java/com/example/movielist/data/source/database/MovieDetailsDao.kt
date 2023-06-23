package com.example.movielist.data.source.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movielist.data.source.database.entity.MovieDetailEntity

@Dao
interface MovieDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetails(movieDetailEntity: MovieDetailEntity)

    @Query("SELECT * FROM MovieDetailEntity where movieId = :movieId")
    suspend fun getMovieDetails(movieId: Int) : MovieDetailEntity?
}