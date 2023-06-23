package com.example.movielist.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.movielist.data.repository.MainRepository
import com.example.movielist.data.source.database.entity.BookmarkMovieEntity
import com.example.movielist.ui.model.BookmarkedMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    val movieList = mainRepository.fetchMovieList(1)
        .catch { exception ->
            // Handle error case
        }.asLiveData()

    suspend fun getBookMarkedMovies() : List<BookmarkMovieEntity> {

        return mainRepository.fetchBookmarkedMovies().flatMapConcat { it.asFlow() }.toList()
    }
}