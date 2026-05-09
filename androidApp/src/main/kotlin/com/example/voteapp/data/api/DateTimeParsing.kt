package com.example.voteapp.data.api

import kotlinx.datetime.LocalDateTime

// kotlinx.datetime LocalDateTime -> epoch millis (assumes UTC)
fun LocalDateTime.toEpochMilliseconds(): Long {
    return this.toInstant(kotlinx.datetime.TimeZone.UTC).toEpochMilliseconds()
}


