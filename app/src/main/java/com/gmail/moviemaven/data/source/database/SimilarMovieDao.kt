package com.gmail.moviemaven.data.source.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gmail.moviemaven.data.source.database.entity.SimilarMovieEntity

@Dao
interface SimilarMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSimilarMovies(similarMovieList: List<SimilarMovieEntity>)

    @Query("SELECT * FROM SimilarMovieEntity WHERE movieId = :movieId")
    suspend fun getSimilarMovies(movieId: Int): List<SimilarMovieEntity>
}