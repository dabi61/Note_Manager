package com.fftools.notemanager.di

import com.fftools.notemanager.repositories.Api
import com.fftools.notemanager.repositories.ApiImpl
import com.fftools.notemanager.repositories.MainLog
import com.fftools.notemanager.repositories.MainLogImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {
    @Binds
    @Singleton
    abstract fun bindMainLog(
        mainLog: MainLogImpl
    ): MainLog

    @Binds
    @Singleton
    abstract fun bindApi(
        api: ApiImpl
    ): Api
}