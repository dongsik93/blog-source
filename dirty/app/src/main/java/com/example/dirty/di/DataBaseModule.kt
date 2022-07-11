package com.example.dirty.di

import android.app.Application
import androidx.room.Room
import com.example.data.db.AppDataBase
import com.example.data.db.dao.DirtyFlagDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDataBase {
        return Room.databaseBuilder(
            application,
            AppDataBase::class.java,
            AppDataBase.DB_NAME
        ).build()
    }

    @Provides
    fun provideDirtyFlagDao(database: AppDataBase): DirtyFlagDao {
        return database.dirtyFlagDao
    }

    @Provides
    fun provideContactDao(appDataBase: AppDataBase) = appDataBase.contactDao
}