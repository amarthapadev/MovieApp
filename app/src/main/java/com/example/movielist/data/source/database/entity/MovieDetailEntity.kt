package com.example.movielist.data.source.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class MovieDetailEntity(
    @PrimaryKey val movieId: Int,
    val overview: String,
    val title: String,
    val backdropPath: String
)