package com.example.voteapp.di

import com.example.voteapp.BuildConfig
import com.example.voteapp.data.api.RetrofitApiService
import com.example.voteapp.data.api.RetrofitVotingsClient
import com.example.voteapp.data.remote.TokenInterceptor
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val CONTENT_TYPE_JSON = "application/json"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val tokenInterceptor = TokenInterceptor(
            firebaseAuth = FirebaseAuth.getInstance()
        )

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        return OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                kotlinx.serialization.json.Json.asConverterFactory(CONTENT_TYPE_JSON.toMediaType())
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitVotingsClient(
        retrofit: Retrofit,
    ): RetrofitVotingsClient {
        return retrofit.create(RetrofitVotingsClient::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofitApiService(
        retrofitVotingsClient: RetrofitVotingsClient,
    ): RetrofitApiService {
        return RetrofitApiService(retrofitVotingsClient)
    }
}
