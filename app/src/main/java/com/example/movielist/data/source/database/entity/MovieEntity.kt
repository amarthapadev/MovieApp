package com.example.movielist.data.source.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    var page: Int = 0,
    @PrimaryKey val movieId: Int,
    val movieName: String,
    val releaseDate: String,
    val posterUrl: String
)