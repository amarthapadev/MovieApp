package com.gmail.moviemaven.data.source.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class MovieReviewEntity(
    @PrimaryKey val movieId: Int,
    val name: String,
    val review: String
)