package com.example.voteapp.notifications

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.voteapp.domain.usecase.GetVotingsUseCase
import java.util.concurrent.TimeUnit

object WorkScheduler {

    fun scheduleVotingReminderWork(context: Context) {
        val request = PeriodicWorkRequest.Builder(
            VotingReminderWorker::class.java,
            1, TimeUnit.HOURS,
        )
            .setInitialDelay(1, TimeUnit.MINUTES)
            .addTag("voting-reminder")
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "voting-reminder-work",
            ExistingPeriodicWorkPolicy.KEEP,
            request,
        )
    }
}

