package com.example.dirty.di

import com.example.domain.usecase.GetAllContactUseCase
import com.example.presentation.usecase.GetAllContactUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    @ViewModelScoped
    fun provideGetAllContactUseCase(useCase: GetAllContactUseCaseImpl): GetAllContactUseCase = useCase
}