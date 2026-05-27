package com.example.voteapp.data.api

import com.example.voteapp.data.remote.TokenInterceptor
import com.example.voteapp.data.remote.VoteApiConfig
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RetrofitProvider {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        firebaseAuth: FirebaseAuth,
        config: VoteApiConfig,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(TokenInterceptor(firebaseAuth))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        config: VoteApiConfig,
    ): Retrofit {
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

        return Retrofit.Builder()
            .baseUrl(config.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideVotingsClient(retrofit: Retrofit): RetrofitVotingsClient =
        retrofit.create()
}

