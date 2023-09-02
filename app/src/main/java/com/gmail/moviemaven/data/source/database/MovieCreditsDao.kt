package com.gmail.moviemaven.data.source.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gmail.moviemaven.data.source.database.entity.MovieCreditEntity

@Dao
interface MovieCreditsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieCredits(creditList: List<MovieCreditEntity>)

    @Query("SELECT * FROM MovieCreditEntity WHERE movieId = :movieId")
    suspend fun getMovieCredits(movieId: Int): List<MovieCreditEntity>
}



