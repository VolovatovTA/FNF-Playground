package com.example.fnfplayground

import android.app.Application
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import com.example.fnfplayground.services.ServiceForMusic
import android.widget.Toast




class MyViewModel(application: Application) : AndroidViewModel(application) {

    var service: ServiceForMusic? = null
    private lateinit var sCon : ServiceConnection
    var intent_ : Intent

    init {
        intent_ = Intent(application.baseContext, ServiceForMusic::class.java)

        sCon = object: ServiceConnection {
            override fun onServiceConnected(p0: ComponentName?, p1: IBinder) {
                service = (p1 as ServiceForMusic.BinderMusic).service
                playMusic()
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


    override fun onCleared() {
        service = null
        super.onCleared()
    }

    fun playMusic() {
        service?.startService(intent_)
    }



}