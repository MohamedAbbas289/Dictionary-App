package com.example.dictionaryapp.feature_dictionary.di

import com.example.dictionaryapp.feature_dictionary.data.repo.WordInfoRepositoryImpl
import com.example.dictionaryapp.feature_dictionary.domain.repo.WordInfoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BindModule {

    @Binds
    abstract fun bindWordInfoRepository(
        wordInfoRepositoryImpl: WordInfoRepositoryImpl
    ): WordInfoRepository
}