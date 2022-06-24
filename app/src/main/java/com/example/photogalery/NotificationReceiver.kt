package com.example.photogalery

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null) {
            Log.i(TAG, "received broadcast: ${intent.action}")
        }
    }

    companion object {
        private const val TAG = "NotificationReceiver"
    }
}