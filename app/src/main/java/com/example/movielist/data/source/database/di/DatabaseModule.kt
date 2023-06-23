package com.example.movielist.data.source.database.di

import android.app.Application
import androidx.room.Room
import com.example.movielist.data.source.database.BookmarkMovieDao
import com.example.movielist.data.source.database.MovieCreditsDao
import com.example.movielist.data.source.database.MovieDao
import com.example.movielist.data.source.database.MovieDatabase
import com.example.movielist.data.source.database.MovieDetailsDao
import com.example.movielist.data.source.database.MovieReviewDao
import com.example.movielist.data.source.database.SimilarMovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        application: Application,
    ): MovieDatabase {
        return Room
            .databaseBuilder(application, MovieDatabase::class.java, "Movie.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(movieDatabase: MovieDatabase): MovieDao {

        return movieDatabase.movieDao()
    }

    @Provides
    @Singleton
    fun provideMovieDetailDao(movieDatabase: MovieDatabase): MovieDetailsDao {

        return movieDatabase.movieDetailsDao()
    }

    @Provides
    @Singleton
    fun provideMovieReviewDao(movieDatabase: MovieDatabase): MovieReviewDao {

        return movieDatabase.movieReviewDao()
    }

    @Provides
    @Singleton
    fun provideMovieCreditsDao(movieDatabase: MovieDatabase): MovieCreditsDao {

        return movieDatabase.movieCreditsDao()
    }

    @Provides
    @Singleton
    fun provideSimilarMovieDao(movieDatabase: MovieDatabase): SimilarMovieDao {

        return movieDatabase.similarMovieDao()
    }

    @Provides
    @Singleton
    fun provideBookmarkMovieDao(movieDatabase: MovieDatabase): BookmarkMovieDao {

        return movieDatabase.bookmarkedMovieDao()
    }
}