package com.gmail.moviemaven.ui.model

class SimilarMovie(
    val id: Int,
    private val posterPath: String
) {

    fun getPosterPath(): String {

        return "https://image.tmdb.org/t/p/w185$posterPath"
    }
}