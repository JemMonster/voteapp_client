package com.example.voteapp.data.remote

import com.google.firebase.auth.FirebaseAuth
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(
    private val firebaseAuth: FirebaseAuth,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val token = firebaseAuth.currentUser?.getIdToken(false)?.result

        if (token.isNullOrBlank()) {
            return chain.proceed(original)
        }

        val newReq = original.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()

        return chain.proceed(newReq)
    }
}

