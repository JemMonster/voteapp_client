package com.example.voteapp.di

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
    fun provideApiService(): ApiService = ApiServiceImpl()
    
    // Firebase later
}

interface ApiService {
    // suspend fun getVotings(): List<Voting>
}

class ApiServiceImpl : ApiService {
    override fun getVotings(): List<Voting> = listOf() // Mock
}

