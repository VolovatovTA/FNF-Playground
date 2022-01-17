package com.example.fnfplayground

import android.app.Application
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import com.example.fnfplayground.services.ServiceForMusic


class MyViewModel(application: Application) : AndroidViewModel(application) {

    var service: ServiceForMusic? = null
    val TAG = "DebugAnimation"

    private lateinit var sCon : ServiceConnection
    var intent_ : Intent


    init {
        intent_ = Intent(application.baseContext, ServiceForMusic::class.java)

        sCon = object: ServiceConnection {
            override fun onServiceConnected(p0: ComponentName?, p1: IBinder) {
                service = (p1 as ServiceForMusic.BinderMusic).service
                playMusicIfItNotPlaying(application)
            }

            override fun onServiceDisconnected(p0: ComponentName?) {
                service?.stopService(intent_)
            }

        }

        application.baseContext.bindService(
            intent_,
            sCon, AppCompatActivity.BIND_AUTO_CREATE
        )
    }
    fun pauseMusic(){
        service?.player?.pause()
    }


    override fun onCleared() {
        Log.d(TAG, "onCleared")

        service!!.stopSelf()
        super.onCleared()
    }

    fun playMusicIfItNotPlaying(application: Application) {
        if (service == null){
            application.baseContext.bindService(
                intent_,
                sCon, AppCompatActivity.BIND_AUTO_CREATE
            )
        }else if (!service!!.isPlayingMusic) {
            service!!.player!!.start()
        }
        else {
            Log.d(TAG, "not")
        }


    }



}