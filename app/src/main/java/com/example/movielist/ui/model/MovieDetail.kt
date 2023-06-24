package com.example.movielist.ui.model

class MovieDetail(
    val title: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    private val backDropPath: String,
) {

    fun getBackDropUrl() : String {

        return "https://image.tmdb.org/t/p/w780$backDropPath"
    }
}