package com.example.firebasenewsexample.data.di

import com.example.firebasenewsexample.data.repository.AuthRepository
import com.example.firebasenewsexample.data.repository.AuthRepositoryImpl
import com.example.firebasenewsexample.data.repository.NewsRepository
import com.example.firebasenewsexample.data.repository.NewsRepositoryImpl
import com.example.firebasenewsexample.data.repository.UserRepository
import com.example.firebasenewsexample.data.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository =
        authRepositoryImpl

    @Provides
    @Singleton
    fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository =
        userRepositoryImpl

    @Provides
    @Singleton
    fun provideNewsRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository =
        newsRepositoryImpl

}