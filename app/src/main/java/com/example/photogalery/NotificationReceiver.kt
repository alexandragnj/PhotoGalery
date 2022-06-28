package com.example.photogalery

import android.app.Activity
import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationManagerCompat

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent) {
        Log.i(TAG, "received result: $resultCode")
        if (resultCode != Activity.RESULT_OK) {
            // A foreground activity canceled the broadcast
            return
        }

        val requestCode = intent.getIntExtra(PollWorker.REQUEST_CODE, 0)
        val notification: Notification? = intent.getParcelableExtra(PollWorker.NOTIFICATION)

        val notificationManager: NotificationManagerCompat? = context?.let {
            NotificationManagerCompat.from(
                it
            )
        }
        if (notification != null) {
            notificationManager?.notify(requestCode, notification)
        }
    }

    companion object {
        private const val TAG = "NotificationReceiver"
    }
}