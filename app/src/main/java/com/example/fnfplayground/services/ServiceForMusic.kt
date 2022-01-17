package com.example.fnfplayground.services


import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Handler
import android.util.Log
import com.example.fnfplayground.R
import com.example.fnfplayground.config.Prefs
import kotlin.properties.Delegates


class ServiceForMusic : Service(), Runnable {

    val TAG = "DebugAnimation"
    var isPlayingMusic = false
    var player: MediaPlayer? = null
    var volumeMusic by Delegates.notNull<Float>()
    var t : Thread? = Thread(this)


    inner class BinderMusic : Binder(){
        val service: ServiceForMusic
            get() = this@ServiceForMusic
    }

    override fun onRebind(intent: Intent?) {
        Log.d(TAG, "onRebind")

        super.onRebind(intent)
    }

    override fun onBind(intent: Intent?): BinderMusic {
        Log.d(TAG, "onBind")

        return BinderMusic()
    }

    override fun onCreate() {
        Log.d(TAG, "onCreate")


        val settings = getSharedPreferences(Prefs.APP_PREFERENCES, Context.MODE_PRIVATE)
        volumeMusic = settings.getFloat(Prefs.APP_PREFERENCES_VOLUME_MUSIC, 0.3f)

        player = MediaPlayer.create(this, R.raw.title1)
        player?.isLooping = true // зацикливаем
        player?.setVolume(volumeMusic, volumeMusic)

    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        player!!.stop()
        player = null
        t?.interrupt()
        t = null
    }

    override fun onStart(intent: Intent?, startid: Int) {
        Log.d(TAG, "onStart")

        if (!isPlayingMusic){
            t?.start()
        }
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "onUnbind")

        return true
    }

    override fun run() {
        isPlayingMusic = true
        player!!.start()
    }

}