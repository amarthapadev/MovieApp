package com.gmail.moviemaven.data.repository.di

import com.gmail.moviemaven.data.repository.DetailRepository
import com.gmail.moviemaven.data.repository.DetailRepositoryImpl
import com.gmail.moviemaven.data.repository.MainRepository
import com.gmail.moviemaven.data.repository.MainRepositoryImpl
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
