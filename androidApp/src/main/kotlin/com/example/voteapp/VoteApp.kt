package com.example.voteapp

import android.app.Application
import android.util.Log
import com.example.voteapp.notifications.WorkScheduler
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class VoteApp : Application() {

    override fun onCreate() {
        super.onCreate()
        try {
            WorkScheduler.scheduleVotingReminderWork(this)
        } catch (e: Exception) {
            Log.e("VoteApp", "Failed to schedule work", e)
        }
    }
}

