package com.example.voteapp.domain.auth

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class FirebaseEmailPasswordAuthRepository(
    private val firebaseAuth: FirebaseAuth,
) : EmailPasswordAuthRepository {

    override suspend fun signIn(email: String, password: String): AuthResult {
        return firebaseAuth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun signUp(email: String, password: String): AuthResult {
        return firebaseAuth.createUserWithEmailAndPassword(email, password).await()
    }
}

