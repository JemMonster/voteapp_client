package com.example.voteapp.di

import com.example.voteapp.data.api.ApiService
import com.example.voteapp.data.api.RetrofitApiService
import com.example.voteapp.data.repository.VotingRepositoryImpl
import com.example.voteapp.domain.port.VotingRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsVotingRepository(
        votingRepositoryImpl: VotingRepositoryImpl
    ): VotingRepository
}
