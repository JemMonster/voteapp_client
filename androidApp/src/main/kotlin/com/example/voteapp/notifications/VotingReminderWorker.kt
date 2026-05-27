package com.example.voteapp.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.voteapp.R
import com.example.voteapp.domain.usecase.GetVotingsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

/**
 * Periodic task: fetch active votings and notify if endTime is close.
 * Strategy (simple MVP):
 * - run every hour
 * - for each ACTIVE voting: if endTime - now <= 1 hour and > 0 => notify
 */
@HiltWorker
class VotingReminderWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val getVotingsUseCase: GetVotingsUseCase,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val nowMs = System.currentTimeMillis()
        val notifyBeforeMs = TimeUnit.HOURS.toMillis(1)

        val votings = runCatching {
            getVotingsUseCase()
        }.getOrElse { return@withContext Result.retry() }




        ensureChannel()

        votings
            .filter { it.status.name == "ACTIVE" }
            .forEach { voting ->
                val diff = voting.endsAt - nowMs
                if (diff in 0..notifyBeforeMs) {
                    showNotification(voting.id, voting.title)
                }
            }

        Result.success()
    }

    private fun ensureChannel() {
        val mgr = applicationContext.getSystemService(NotificationManager::class.java)
        val channelId = CHANNEL_ID
        if (mgr.getNotificationChannel(channelId) == null) {
            val channel = NotificationChannel(
                channelId,
                "Voting reminders",
                NotificationManager.IMPORTANCE_DEFAULT,
            )
            mgr.createNotificationChannel(channel)
        }
    }

    private fun showNotification(votingId: String, title: String) {
        val notifId = votingId.hashCode()
        val channelId = CHANNEL_ID

        val builder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Скоро завершится голосование")
            .setContentText(title)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        NotificationManagerCompat.from(applicationContext).notify(notifId, builder.build())
    }

    companion object {
        private const val CHANNEL_ID = "voteapp_voting_reminders"
    }
}

