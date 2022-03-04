package com.example.services_test_kotlin

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import io.flutter.embedding.android.FlutterActivity
import net.gotev.uploadservice.BuildConfig
import net.gotev.uploadservice.UploadServiceConfig

class App : FlutterActivity() {

    companion object {
        const val notificationChannelID = "TestChannel"
    }

    // Customize the notification channel as you wish. This is only for a bare minimum example
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            val channel = NotificationChannel(
                notificationChannelID,
                "TestApp Channel",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createNotificationChannel()

        UploadServiceConfig.initialize(
            context = context as Application,
            defaultNotificationChannel = notificationChannelID,
            debug = BuildConfig.DEBUG
        )
    }
}