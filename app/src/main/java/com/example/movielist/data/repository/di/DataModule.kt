package com.example.movielist.data.repository.di

import com.example.movielist.data.repository.DetailRepository
import com.example.movielist.data.repository.DetailRepositoryImpl
import com.example.movielist.data.repository.MainRepository
import com.example.movielist.data.repository.MainRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {

    @Binds
    fun bindsMainRepository(
        mainRepositoryImpl: MainRepositoryImpl
    ): MainRepository

    @Binds
    fun bindsDetailRepository(
        detailRepositoryImpl: DetailRepositoryImpl
    ): DetailRepository
}
