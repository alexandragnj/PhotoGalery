package com.example.photogalery

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.photogalery.PhotoGalleryApplication.Companion.NOTIFICATION_CHANNEL_ID
import com.example.photogalery.model.GalleryItem

class PollWorker(private val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun doWork(): Result {
        val query = QueryPreferences.getStoredQuery(context)
        val lastResultId = QueryPreferences.getLastResultId(context)
        val items: List<GalleryItem> = if (query.isEmpty()) {
            FlickrFetchr().fetchPhotoRequest()
                .execute()
                .body()
                ?.photos
                ?.galleryItems
        } else {
            FlickrFetchr().searchPhotosRequest(query)
                .execute()
                .body()
                ?.photos
                ?.galleryItems
        } ?: emptyList()

        if (items.isEmpty()) {
            return Result.success()
        }

        val resultId = items.first().id
        if (resultId == lastResultId) {
            Log.i(TAG, "Got an old result: $resultId")
        } else {
            Log.i(TAG, "Got a new result: $resultId")
            QueryPreferences.setLastResultId(context, resultId)

            val intent = PhotoGalleryActivity.newIntent(context)

            val pendingIntent: PendingIntent? =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_MUTABLE)
                } else {
                    PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)
                }

            val resources = context.resources
            val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setTicker(resources.getString(R.string.new_pictures_title))
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle(resources.getString(R.string.new_pictures_title))
                .setContentText(resources.getString(R.string.new_pictures_text))
                .setContentIntent(pendingIntent).setAutoCancel(true).build()
            val notificationManager =
                NotificationManagerCompat.from(context)
            notificationManager.notify(0, notification)

            context.sendBroadcast(Intent(ACTION_SHOW_NOTIFICATION), PERM_PRIVATE)
        }
        return Result.success()
    }

    companion object {
        private const val TAG = "PollWorker"
        const val ACTION_SHOW_NOTIFICATION = "com.example.photogalery.SHOW_NOTIFICATION"
        const val PERM_PRIVATE = "com.example.photogalery.PRIVATE"
    }
}