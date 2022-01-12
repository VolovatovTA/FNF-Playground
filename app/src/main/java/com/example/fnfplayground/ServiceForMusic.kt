package com.example.fnfplayground

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast


import android.media.MediaPlayer




class ServiceForMusic : Service() {

    var player: MediaPlayer? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        Toast.makeText(this, "My Service Created", Toast.LENGTH_LONG).show()
        player = MediaPlayer.create(this, R.raw.title1)
        player!!.isLooping = true // зацикливаем
    }

    override fun onDestroy() {
        Toast.makeText(this, "My Service Stopped", Toast.LENGTH_LONG).show()
        player!!.stop()
    }

    override fun onStart(intent: Intent?, startid: Int) {
        Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show()
        player!!.start()
    }
}