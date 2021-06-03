package com.search.giphy.search.di

import com.search.giphy.search.data.GifsRepositoryImpl
import com.search.giphy.search.domain.GifsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideGifsRepository(gifsRepository: GifsRepositoryImpl): GifsRepository
}