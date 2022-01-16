package com.example.fnfplayground.services

import android.app.IntentService
import android.app.Notification
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.widget.Toast


import android.media.MediaPlayer
import android.os.Binder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.fnfplayground.R
import kotlin.properties.Delegates


class ServiceForMusic : Service(), Runnable {

    val TAG = "DebugAnimation"
    val CHANNEL_ID = "1003"
    var isPlayingMusic = false
    var player: MediaPlayer? = null
    val APP_PREFERENCES = "mySettings"
    val APP_PREFERENCES_VOLUME_MUSIC = "volumeMusic"
    var volumeMusic by Delegates.notNull<Float>()
    var t : Thread? = Thread(this)


    inner class BinderMusic : Binder(){
        val service: ServiceForMusic
            get() = this@ServiceForMusic
    }


    override fun onBind(intent: Intent?): BinderMusic {
        Log.d(TAG, "ServiceForMusic onBind")
        return BinderMusic()
    }

    override fun onCreate() {
        Log.d(TAG, "ServiceForMusic onCreate")

        val settings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        volumeMusic = settings.getFloat(APP_PREFERENCES_VOLUME_MUSIC, 0.3f)

        player = MediaPlayer.create(this, R.raw.title1)
        player?.isLooping = true // зацикливаем
        player?.setVolume(volumeMusic, volumeMusic)

    }

    override fun onDestroy() {
        Log.d(TAG, "ServiceForMusic onDestroy")

        player!!.stop()
        player = null
        t?.interrupt()
        t = null

    }

    override fun onStart(intent: Intent?, startid: Int) {
        Log.d(TAG, "ServiceForMusic onStart")
//        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
//            .setContentTitle("My notification")
//            .setContentText("Much longer text that cannot fit one line...")
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//        startForeground(1003, notification.build())

        if (!isPlayingMusic){
            t?.start()
        }

        Log.d(TAG, "y = $t")
        Log.d(TAG, "y = ${t?.isAlive}")

    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "ServiceForMusic onUnbind")

        return super.onUnbind(intent)
    }


    override fun run() {
        isPlayingMusic = true
        player!!.start()
    }

}