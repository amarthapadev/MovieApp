package com.gmail.moviemaven.ui.model

data class Movie(
    var page: Int = 0,
    val movieId: Int,
    val name: String,
    val releaseDate: String,
    private val posterPath: String
) {

    fun getPosterUrl(): String {

        return "https://image.tmdb.org/t/p/w185$posterPath"
    }
}