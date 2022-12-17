package com.example.marvel.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.marvel.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushNotificationService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.data.isNotEmpty()) {
            val id:String= remoteMessage.data["id"].toString()
            sendNotification(id)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendNotification(id:String) {
        val channelId = "fcm_default_channel"

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            channelId,
            "Channel",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)


        val taskDetailIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("deeplink://app.com/${id}")
        )

        val pending: PendingIntent = TaskStackBuilder.create(applicationContext).run {
            addNextIntentWithParentStack(taskDetailIntent)
            getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)
        }

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(applicationContext, channelId)
            .setContentIntent(pending)
            .setSmallIcon(R.drawable.emoji)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(getString(R.string.notification_body))
            .setAutoCancel(true)
            .setSound(defaultSoundUri)

        notificationManager.notify(1 /* ID of notification */, notificationBuilder.build())
    }

}
