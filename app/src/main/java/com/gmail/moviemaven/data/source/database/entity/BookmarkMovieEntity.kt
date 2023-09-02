package com.gmail.moviemaven.data.source.database.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookmarkMovieEntity(
    @PrimaryKey
    @NonNull
    val id: Int,
    val title: String
)