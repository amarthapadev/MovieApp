package com.example.movielist.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.movielist.data.repository.DetailRepository
import com.example.movielist.ui.model.Credit
import com.example.movielist.ui.model.MovieDetail
import com.example.movielist.ui.model.MovieReview
import com.example.movielist.ui.model.SimilarMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val
    detailRepository: DetailRepository
) : ViewModel() {

    fun getDetails(movieId: Int): LiveData<MovieDetail?> {

        return detailRepository.fetchMovieDetails(movieId).catch { exception ->
        }.asLiveData()
    }

    fun getReview(movieId: Int): LiveData<MovieReview?> {

        return detailRepository.fetchMovieReview(movieId).catch { exception ->
        }.asLiveData()
    }

    fun getCredits(movieId: Int): LiveData<List<Credit>> {

        return detailRepository.fetchMovieCredits(movieId).catch { exception -> }.asLiveData()
    }

    fun getSimilarMovies(movieId: Int): LiveData<List<SimilarMovie>> {

        return detailRepository.fetchSimilarMovies(movieId).catch { exception -> }.asLiveData()
    }
}