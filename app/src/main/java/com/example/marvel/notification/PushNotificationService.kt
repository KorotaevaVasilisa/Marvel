package com.example.marvel.notification

import com.google.firebase.messaging.FirebaseMessagingService

class PushNotificationService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}