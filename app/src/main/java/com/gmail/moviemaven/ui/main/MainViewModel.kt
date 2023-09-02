package com.gmail.moviemaven.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gmail.moviemaven.data.repository.MainRepository
import com.gmail.moviemaven.ui.model.Movie
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
            exception.printStackTrace()
        }.asLiveData()

    suspend fun getBookMarkedMovies(): List<Movie> {

        return mainRepository.fetchBookmarkedMovies().flatMapConcat { it.asFlow() }.toList()
    }
}