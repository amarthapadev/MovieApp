package com.gmail.moviemaven.data.repository

import com.gmail.moviemaven.data.source.database.BookmarkMovieDao
import com.gmail.moviemaven.data.source.database.MovieCreditsDao
import com.gmail.moviemaven.data.source.database.MovieDetailsDao
import com.gmail.moviemaven.data.source.database.MovieReviewDao
import com.gmail.moviemaven.data.source.database.SimilarMovieDao
import com.gmail.moviemaven.data.source.database.entity.mapper.toEntity
import com.gmail.moviemaven.data.source.database.entity.mapper.toExternal
import com.gmail.moviemaven.data.source.network.Dispatcher
import com.gmail.moviemaven.data.source.network.MovieAppDispatchers
import com.gmail.moviemaven.data.source.network.service.MovieClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val movieClient: MovieClient,
    private val movieDetailsDao: MovieDetailsDao,
    private val movieReviewDao: MovieReviewDao,
    private val movieCreditsDao: MovieCreditsDao,
    private val similarMovieDao: SimilarMovieDao,
    private val bookmarkMovieDao: BookmarkMovieDao,
    @Dispatcher(MovieAppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : com.gmail.moviemaven.data.repository.DetailRepository {

    // TODO handle error cases
    override fun fetchMovieDetails(movieId: Int) = flow {

        val details = movieDetailsDao.getMovieDetails(movieId)?.toExternal()

        if (details != null) {

            emit(details)
        } else {

            val response = movieClient.fetchMovieDetails(movieId)
            movieDetailsDao.insertMovieDetails(response.toEntity())

            emit(movieDetailsDao.getMovieDetails(movieId)?.toExternal())
        }
    }.flowOn(ioDispatcher)

    override fun fetchMovieReview(movieId: Int) = flow {

        val review = movieReviewDao.getMovieReview(movieId)?.toExternal()
        if (review != null) {
            emit(review)
        } else {

            val response = movieClient.fetchMovieReview(movieId)
            movieReviewDao.insetMovieReview(response.toEntity())

            emit(movieReviewDao.getMovieReview(movieId)?.toExternal())
        }
    }.flowOn(ioDispatcher)

    override fun fetchMovieCredits(movieId: Int) = flow {

        val creditList = movieCreditsDao.getMovieCredits(movieId).toExternal()
        if (creditList.isNotEmpty()) {

            emit(creditList)
        } else {

            val response = movieClient.fetchMovieCredits(movieId)

            val entityList = response.cast.toEntity()
            entityList.map { it.movieId = movieId }

            movieCreditsDao.insertMovieCredits(entityList)

            emit(movieCreditsDao.getMovieCredits(movieId).toExternal())
        }
    }.flowOn(ioDispatcher)

    override fun fetchSimilarMovies(movieId: Int) = flow {

        val similarMovies = similarMovieDao.getSimilarMovies(movieId).toExternal()

        if (similarMovies.isNotEmpty()) {

            emit(similarMovies)
        } else {

            val response = movieClient.fetchSimilarMovies(movieId).results

            val entityList = response.toEntity()
            entityList.map { it.movieId = movieId }

            similarMovieDao.insertSimilarMovies(entityList)

            emit(similarMovieDao.getSimilarMovies(movieId).toExternal())
        }
    }.flowOn(ioDispatcher)
}