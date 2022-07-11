package com.example.dirty.di

import com.example.data.repository.ContactRepositoryImpl
import com.example.data.repository.DirtyFlagRepositoryImpl
import com.example.domain.repository.ContactRepository
import com.example.domain.repository.DirtyFlagRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideDirtyFlagRepository(repository: DirtyFlagRepositoryImpl): DirtyFlagRepository = repository

    @Singleton
    @Provides
    fun provideContactRepository(repository: ContactRepositoryImpl): ContactRepository = repository
}