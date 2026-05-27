package com.example.voteapp.di

import com.example.voteapp.domain.port.VotingRepository
import com.example.voteapp.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetVotingsUseCase(
        repository: VotingRepository
    ): GetVotingsUseCase = GetVotingsUseCase(repository)

    @Provides
    @Singleton
    fun provideGetVotingDetailUseCase(
        repository: VotingRepository
    ): GetVotingDetailUseCase = GetVotingDetailUseCase(repository)

    @Provides
    @Singleton
    fun provideGetVotingHistoryUseCase(
        repository: VotingRepository
    ): GetVotingHistoryUseCase = GetVotingHistoryUseCase(repository)

    @Provides
    @Singleton
    fun provideSubmitVoteUseCase(
        repository: VotingRepository
    ): SubmitVoteUseCase = SubmitVoteUseCase(repository)

    @Provides
    @Singleton
    fun provideGetVotingResultsUseCase(
        repository: VotingRepository
    ): GetVotingResultsUseCase = GetVotingResultsUseCase(repository)

    @Provides
    @Singleton
    fun provideCreateVotingUseCase(
        repository: VotingRepository
    ): CreateVotingUseCase = CreateVotingUseCase(repository)

    @Provides
    @Singleton
    fun provideInviteToVotingUseCase(
        repository: VotingRepository
    ): InviteToVotingUseCase = InviteToVotingUseCase(repository)
}
