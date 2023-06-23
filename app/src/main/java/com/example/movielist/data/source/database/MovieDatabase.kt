package com.example.movielist.data.source.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movielist.data.source.database.entity.BookmarkMovieEntity
import com.example.movielist.data.source.database.entity.MovieCreditEntity
import com.example.movielist.data.source.database.entity.MovieDetailEntity
import com.example.movielist.data.source.database.entity.MovieEntity
import com.example.movielist.data.source.database.entity.MovieReviewEntity
import com.example.movielist.data.source.database.entity.SimilarMovieEntity

@Database(
    entities = [MovieEntity::class, MovieDetailEntity::class, MovieReviewEntity::class,
        MovieCreditEntity::class, SimilarMovieEntity::class, BookmarkMovieEntity::class],
    version = 6
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun movieDetailsDao(): MovieDetailsDao
    abstract fun movieReviewDao(): MovieReviewDao
    abstract fun movieCreditsDao(): MovieCreditsDao
    abstract fun similarMovieDao(): SimilarMovieDao
    abstract fun bookmarkedMovieDao(): BookmarkMovieDao
}