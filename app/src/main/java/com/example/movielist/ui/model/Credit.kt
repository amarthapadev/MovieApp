package com.example.movielist.ui.model

class Credit(
    val name: String,
    val characterName: String,
    val profilePath: String
) {

    fun getProfilePicUrl() : String {

        return "https://image.tmdb.org/t/p/w185$profilePath"
    }
}