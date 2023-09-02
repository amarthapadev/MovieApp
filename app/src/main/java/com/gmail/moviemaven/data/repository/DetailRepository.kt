package com.gmail.moviemaven.data.repository

import com.gmail.moviemaven.ui.model.Credit
import com.gmail.moviemaven.ui.model.MovieDetail
import com.gmail.moviemaven.ui.model.MovieReview
import com.gmail.moviemaven.ui.model.SimilarMovie
import kotlinx.coroutines.flow.Flow

interface DetailRepository {

    fun fetchMovieDetails(movieId: Int): Flow<MovieDetail?>

    fun fetchMovieReview(movieId: Int): Flow<MovieReview?>

    fun fetchMovieCredits(movieId: Int): Flow<List<Credit>>

    fun fetchSimilarMovies(movieId: Int): Flow<List<SimilarMovie>>
}