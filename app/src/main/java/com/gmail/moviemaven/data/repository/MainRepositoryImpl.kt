package com.gmail.moviemaven.data.repository

import com.gmail.moviemaven.data.source.database.BookmarkMovieDao
import com.gmail.moviemaven.data.source.database.MovieDao
import com.gmail.moviemaven.data.source.database.entity.mapper.toEntity
import com.gmail.moviemaven.data.source.database.entity.mapper.toExternal
import com.gmail.moviemaven.data.source.network.Dispatcher
import com.gmail.moviemaven.data.source.network.MovieAppDispatchers
import com.gmail.moviemaven.data.source.network.service.MovieClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val movieClient: MovieClient,
    private val movieDao: MovieDao,
    private val bookmarkMovieDao: BookmarkMovieDao,
    @Dispatcher(MovieAppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : MainRepository {

    // TODO handle error cases
    override fun fetchMovieList(page: Int) = flow {

        val movies = movieDao.getMovieList(page).toExternal()
        if (movies.isEmpty()) {

            val response = movieClient.fetchMovieList(page = page)
            val movieList = response.toEntity()
            movieList.forEach { movie -> movie.page = page }

            movieDao.insertMovieList(movieList)

            emit(movieDao.getAllMovieList(page).toExternal())
        } else {

            emit(movieDao.getAllMovieList(page).toExternal())
        }
    }.flowOn(ioDispatcher)

    override fun fetchBookmarkedMovies() = flow {

        val list = bookmarkMovieDao.getAllMovies().map { it.id }

        val movies = bookmarkMovieDao.getMoviesById(list)

        emit(movies.toExternal())

    }.flowOn(ioDispatcher)
}