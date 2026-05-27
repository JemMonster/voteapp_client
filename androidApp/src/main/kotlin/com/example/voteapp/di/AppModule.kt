package com.example.voteapp.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Legacy module. Most dependencies are now provided by:
 * - NetworkModule
 * - RepositoryModule
 * - UseCaseModule
 *
 * This module is kept for backwards compatibility.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule



