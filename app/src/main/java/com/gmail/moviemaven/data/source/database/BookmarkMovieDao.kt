package com.gmail.moviemaven.data.source.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gmail.moviemaven.data.source.database.entity.BookmarkMovieEntity
import com.gmail.moviemaven.data.source.database.entity.MovieEntity

@Dao
interface BookmarkMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(entity: BookmarkMovieEntity)

    @Delete
    fun deleteMovie(entity: BookmarkMovieEntity)

    @Query("SELECT * FROM BookmarkMovieEntity WHERE id = :movieId")
    fun getMovieById(movieId: String): BookmarkMovieEntity

    @Query("SELECT * FROM MovieEntity WHERE movieId IN (:movieIds)")
    fun getMoviesById(movieIds: List<Int>): List<MovieEntity>

    @Query("SELECT * FROM BookmarkMovieEntity")
    fun getAllMovies(): List<BookmarkMovieEntity>
}