package com.example.voteapp.data.api

import kotlinx.serialization.Serializable

/**
 * DTO versions of domain enums - to avoid dependency from data to domain
 */

@Serializable
enum class VotingTypeDto(val value: String) {
    SINGLE("SINGLE"),
    MULTIPLE("MULTIPLE"),
    PETITION("PETITION"),
    RAFFLE("RAFFLE");

    fun toDomain(): com.example.voteapp.domain.model.VotingType {
        return when (this) {
            SINGLE -> com.example.voteapp.domain.model.VotingType.SINGLE
            MULTIPLE -> com.example.voteapp.domain.model.VotingType.MULTIPLE
            PETITION -> com.example.voteapp.domain.model.VotingType.PETITION
            RAFFLE -> com.example.voteapp.domain.model.VotingType.RAFFLE
        }
    }

    companion object {
        fun fromDomain(type: com.example.voteapp.domain.model.VotingType): VotingTypeDto {
            return when (type) {
                com.example.voteapp.domain.model.VotingType.SINGLE -> SINGLE
                com.example.voteapp.domain.model.VotingType.MULTIPLE -> MULTIPLE
                com.example.voteapp.domain.model.VotingType.PETITION -> PETITION
                com.example.voteapp.domain.model.VotingType.RAFFLE -> RAFFLE
            }
        }
    }
}

@Serializable
enum class VotingStatusDto(val value: String) {
    ACTIVE("ACTIVE"),
    CLOSED("CLOSED");

    fun toDomain(): com.example.voteapp.domain.model.VotingStatus {
        return when (this) {
            ACTIVE -> com.example.voteapp.domain.model.VotingStatus.ACTIVE
            CLOSED -> com.example.voteapp.domain.model.VotingStatus.CLOSED
        }
    }

    companion object {
        fun fromDomain(status: com.example.voteapp.domain.model.VotingStatus): VotingStatusDto {
            return when (status) {
                com.example.voteapp.domain.model.VotingStatus.ACTIVE -> ACTIVE
                com.example.voteapp.domain.model.VotingStatus.CLOSED -> CLOSED
            }
        }
    }
}
