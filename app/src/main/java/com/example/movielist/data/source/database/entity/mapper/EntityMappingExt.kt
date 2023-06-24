package com.example.movielist.data.source.database.entity.mapper

import com.example.movielist.data.source.database.entity.MovieCreditEntity
import com.example.movielist.data.source.database.entity.MovieDetailEntity
import com.example.movielist.data.source.database.entity.MovieEntity
import com.example.movielist.data.source.database.entity.MovieReviewEntity
import com.example.movielist.data.source.database.entity.SimilarMovieEntity
import com.example.movielist.data.source.network.models.credits.Cast
import com.example.movielist.data.source.network.models.details.Details
import com.example.movielist.data.source.network.models.nowPlaying.Result
import com.example.movielist.data.source.network.models.reviews.Reviews
import com.example.movielist.ui.model.Credit
import com.example.movielist.ui.model.Movie
import com.example.movielist.ui.model.MovieDetail
import com.example.movielist.ui.model.MovieReview
import com.example.movielist.ui.model.SimilarMovie

fun MovieEntity.toExternal() = Movie(
    page = page,
    movieId = movieId,
    name = movieName,
    releaseDate = releaseDate,
    posterPath = posterUrl
)

@JvmName("movieEntityToExternal")
fun List<MovieEntity>.toExternal() = map(MovieEntity::toExternal)


fun Result.toEntity() = MovieEntity(
    movieId = id,
    movieName = title,
    releaseDate = release_date,
    posterUrl = poster_path
)

@JvmName("resultToInternal")
fun List<Result>.toEntity() = map(Result::toEntity)


fun MovieDetailEntity.toExternal() = MovieDetail(
    title = title,
    overview = overview,
    backDropPath = backdropPath,
    releaseDate = releaseDate,
    posterPath = posterPath
)

@JvmName("movieDetailEntityToExternal")
fun List<MovieDetailEntity>.toExternal() = map(MovieDetailEntity::toExternal)


fun Details.toEntity() = MovieDetailEntity(
    movieId = id,
    overview = overview,
    title = title,
    backdropPath = backdrop_path,
    releaseDate = release_date,
    posterPath = poster_path
)

@JvmName("detailsToInternal")
fun List<Details>.toEntity() = map(Details::toEntity)


@JvmName("movieReviewEntityToExternal")
fun MovieReviewEntity.toExternal() = MovieReview(
    name = name,
    review = review
)

@JvmName("movieReviewEntityListToExternal")
fun List<MovieReviewEntity>.toExternal() = map(MovieReviewEntity::toExternal)

@JvmName("reviewsToEntity")
fun Reviews.toEntity() = MovieReviewEntity(
    movieId = id,
    name = results[0].author,
    review = results[0].content
)

@JvmName("reviewsListToEntity")
fun List<Reviews>.toEntity() = map(Reviews::toEntity)

@JvmName("creditEntityToExternal")
fun MovieCreditEntity.toExternal() = Credit(
    name = name,
    characterName = characterName,
    profilePath = profilePath
)

@JvmName("creditEntityListToExternal")
fun List<MovieCreditEntity>.toExternal() = map(MovieCreditEntity::toExternal)

@JvmName("castToEntity")
fun Cast.toEntity() = MovieCreditEntity(
    id = id,
    name = name,
    characterName = character,
    profilePath = profile_path ?: ""
)

@JvmName("castListToEntity")
fun List<Cast>.toEntity() = map(Cast::toEntity)


@JvmName("similarMovieEntityToExternal")
fun SimilarMovieEntity.toExternal() = SimilarMovie(
    id = id,
    posterPath = posterPath ?: ""
)

@JvmName("similarMovieEntityListToExternal")
fun List<SimilarMovieEntity>.toExternal() = map(SimilarMovieEntity::toExternal)


@JvmName("resultToSimilarMovieEntity")
fun com.example.movielist.data.source.network.models.similar.Result.toEntity() =
    SimilarMovieEntity(
        id = id,
        posterPath = poster_path ?: ""
    )

@JvmName("similarMovieListToMovieEntity")
fun List<com.example.movielist.data.source.network.models.similar.Result>.toEntity() =
    map(com.example.movielist.data.source.network.models.similar.Result::toEntity)


