package com.example.movielist.data.source.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movielist.data.source.database.entity.MovieReviewEntity

@Dao
interface MovieReviewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insetMovieReview(movieReviewEntity: MovieReviewEntity)

    @Query("SELECT * FROM MovieReviewEntity where movieId = :id")
    suspend fun getMovieReview(id: Int): MovieReviewEntity?
}