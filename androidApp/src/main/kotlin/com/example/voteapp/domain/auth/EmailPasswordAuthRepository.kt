package com.example.voteapp.domain.auth

import com.google.firebase.auth.AuthResult

interface EmailPasswordAuthRepository {
    suspend fun signIn(email: String, password: String): AuthResult
    suspend fun signUp(email: String, password: String): AuthResult
}

