package com.example.movielist.data.source.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class SimilarMovieEntity(
    @PrimaryKey val id: Int,
    var movieId: Int = 0,
    val posterPath: String
)