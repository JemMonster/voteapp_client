package com.example.voteapp.di

import com.example.voteapp.data.api.ApiService
import com.example.voteapp.data.api.KtorApiService
import com.example.voteapp.data.repository.VotingRepositoryImpl
import com.example.voteapp.domain.port.VotingRepository
import com.example.voteapp.domain.usecase.GetVotingsUseCase

import com.example.voteapp.data.remote.HttpClientFactory
import com.example.voteapp.data.remote.VoteApiConfig
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
    fun provideVoteApiConfig(): VoteApiConfig = VoteApiConfig(
        baseUrl = "http://192.168.0.100:8080" // замените при настройке окружения
    )

    @Provides
    @Singleton
    fun provideHttpClient(config: VoteApiConfig): io.ktor.client.HttpClient =
        HttpClientFactory.create(config.baseUrl)

    @Provides
    @Singleton
    fun provideApiService(
        httpClient: io.ktor.client.HttpClient,
    ): ApiService = KtorApiService(httpClient)


    @Provides
    @Singleton
    fun provideVotingRepository(
        apiService: ApiService,
    ): VotingRepository = VotingRepositoryImpl(apiService)

    @Provides
    @Singleton
    fun provideGetVotingsUseCase(
        repository: VotingRepository,
    ): GetVotingsUseCase = GetVotingsUseCase(repository)
}



