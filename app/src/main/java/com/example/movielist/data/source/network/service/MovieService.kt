package com.example.movielist.data.source.network.service

import com.example.movielist.data.source.network.models.credits.Credits
import com.example.movielist.data.source.network.models.details.Details
import com.example.movielist.data.source.network.models.nowPlaying.NowPlaying
import com.example.movielist.data.source.network.models.reviews.Reviews
import com.example.movielist.data.source.network.models.similar.Similar
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/now_playing")
    suspend fun fetchMovieList(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): NowPlaying

    @GET("movie/{movieId}")
    suspend fun fetchMovieDetails(@Path("movieId") movieId: Int): Details

    @GET("movie/{movieId}/reviews")
    suspend fun fetchMovieReviews(@Path("movieId") movieId: Int): Reviews

    @GET("movie/{movieId}/credits")
    suspend fun fetchMovieCredits(@Path("movieId") movieId: Int): Credits

    @GET("movie/{movieId}/similar")
    suspend fun fetchSimilarMovie(@Path("movieId") movieId: Int): Similar
}