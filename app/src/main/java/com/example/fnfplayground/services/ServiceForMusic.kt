package com.example.fnfplayground.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast


import android.media.MediaPlayer
import com.example.fnfplayground.R


class ServiceForMusic : Service() {

    var player: MediaPlayer? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        player = MediaPlayer.create(this, R.raw.title1)
        player?.isLooping = true // зацикливаем
        player?.setVolume(0.3f, 0.3f)
    }

    override fun onDestroy() {

        player!!.stop()
    }

    override fun onStart(intent: Intent?, startid: Int) {
        val r = Runnable {
            player!!.start()

        }
        val t = Thread(r)
        t.start()
    }
}