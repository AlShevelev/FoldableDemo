package com.shevelev.foldable.di

import com.shevelev.foldable.repository.ColorRepository
import com.shevelev.foldable.repository.ColorRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    internal fun provideColorRepository(): ColorRepository = ColorRepositoryImpl()
}
