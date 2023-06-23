package com.example.movielist.data.repository

import com.example.movielist.ui.model.Credit
import com.example.movielist.ui.model.MovieDetail
import com.example.movielist.ui.model.MovieReview
import com.example.movielist.ui.model.SimilarMovie
import kotlinx.coroutines.flow.Flow

interface DetailRepository {

    fun fetchMovieDetails(movieId: Int): Flow<MovieDetail?>

    fun fetchMovieReview(movieId: Int): Flow<MovieReview?>

    fun fetchMovieCredits(movieId: Int): Flow<List<Credit>>

    fun fetchSimilarMovies(movieId: Int): Flow<List<SimilarMovie>>
}