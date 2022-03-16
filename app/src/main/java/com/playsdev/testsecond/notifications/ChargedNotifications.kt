package com.playsdev.testsecond.notifications

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.playsdev.testsecond.R
import com.playsdev.testsecond.view.MainFragment

class ChargedNotifications: BroadcastReceiver()  {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_POWER_CONNECTED){
            createNotification(context!!)
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun createNotification(context: Context){
        val intent = Intent(context, MainFragment::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val notificationBuilder = NotificationCompat.Builder(context, notificationId)
            .setSmallIcon(R.drawable.ic_baseline_map_24)
            .setContentTitle("Title")
            .setContentText("it’s time to explore our Earth!")
            .setAutoCancel(true)
            .addAction(R.drawable.ic_baseline_map_24, "Explore", pendingIntent)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(notificationId, notificationName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }

    companion object{
        private const val notificationName = "com.android.notifcation_new"
        private const val notificationId = "NotificationChannelId"
    }
}