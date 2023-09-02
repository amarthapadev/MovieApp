package com.gmail.moviemaven.data.repository

import com.gmail.moviemaven.ui.model.Movie
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun fetchMovieList(page: Int): Flow<List<Movie>>
    fun fetchBookmarkedMovies(): Flow<List<Movie>>
}

