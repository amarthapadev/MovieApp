package com.example.movielist.data.repository

import com.example.movielist.data.source.database.entity.BookmarkMovieEntity
import com.example.movielist.ui.model.Movie
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun fetchMovieList(page: Int): Flow<List<Movie>>
    fun fetchBookmarkedMovies(): Flow<List<BookmarkMovieEntity>>
}

