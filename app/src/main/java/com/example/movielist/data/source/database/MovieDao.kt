package com.example.movielist.data.source.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movielist.data.source.database.entity.MovieEntity

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieList(movieList: List<MovieEntity>)

    @Query("SELECT * FROM MovieEntity WHERE page = :page_")
    suspend fun getMovieList(page_: Int): List<MovieEntity>

    @Query("SELECT * FROM MovieEntity WHERE page <= :page_")
    suspend fun getAllMovieList(page_: Int): List<MovieEntity>
}