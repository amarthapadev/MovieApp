package com.example.movielist.data.source.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movielist.data.source.database.entity.BookmarkMovieEntity

@Dao
interface BookmarkMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(entity: BookmarkMovieEntity)

    @Delete
    fun deleteMovie(entity: BookmarkMovieEntity)

    @Query("SELECT * FROM BookmarkMovieEntity WHERE id = :movieId")
    fun getMovieById(movieId: String): BookmarkMovieEntity

    @Query("SELECT * FROM BookmarkMovieEntity")
    fun getAllMovies(): List<BookmarkMovieEntity>
}