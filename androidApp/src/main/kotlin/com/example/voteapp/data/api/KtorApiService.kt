package com.example.voteapp.data.api

import com.example.voteapp.domain.model.Voting
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import javax.inject.Inject

class KtorApiService @Inject constructor(
    private val client: HttpClient
) : ApiService {

    override suspend fun getVotings(): List<Voting> {
        // GET /api/v1/votings
        return client.get("api/v1/votings")
    }
}

