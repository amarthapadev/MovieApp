package com.gmail.moviemaven.data.source.network.service

import com.gmail.moviemaven.data.source.network.models.credits.Credits
import com.gmail.moviemaven.data.source.network.models.details.Details
import com.gmail.moviemaven.data.source.network.models.nowPlaying.Result
import com.gmail.moviemaven.data.source.network.models.reviews.Reviews
import com.gmail.moviemaven.data.source.network.models.similar.Similar
import javax.inject.Inject

class MovieClient @Inject constructor(
    private val movieService: MovieService
) {

    suspend fun fetchMovieList(
        page: Int
    ): List<Result> = movieService.fetchMovieList().results // TODO implement paging

    suspend fun fetchMovieDetails(
        movieId: Int
    ): Details = movieService.fetchMovieDetails(movieId = movieId)

    suspend fun fetchMovieReview(
        movieId: Int
    ): Reviews = movieService.fetchMovieReviews(movieId = movieId)

    suspend fun fetchMovieCredits(
        movieId: Int
    ): Credits = movieService.fetchMovieCredits(movieId = movieId)

    suspend fun fetchSimilarMovies(
        movieId: Int
    ): Similar = movieService.fetchSimilarMovie(movieId = movieId)
}
