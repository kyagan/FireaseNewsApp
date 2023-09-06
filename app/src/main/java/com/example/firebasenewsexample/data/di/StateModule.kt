package com.example.firebasenewsexample.data.di

import com.example.firebasenewsexample.data.state.ProfilePhotoUpdateState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StateModule {


    @Singleton
    @Provides
    fun provideProfilePhotoUpdateState(): ProfilePhotoUpdateState = ProfilePhotoUpdateState.Idle

    @Singleton
    @Provides
    fun provideMutableProfilePhotoUpdateState(): MutableSharedFlow<ProfilePhotoUpdateState> = MutableSharedFlow()
}