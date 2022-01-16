package com.example.fnfplayground

import android.content.ComponentName
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import com.example.fnfplayground.services.ServiceForMusic


class MainActivity : AppCompatActivity() {
    val APP_PREFERENCES = "mySettings"
    val TAG = "DebugAnimation"
    lateinit var service: ServiceForMusic
    private lateinit var sCon : ServiceConnection


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "MainActivity onCreate")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sCon = object: ServiceConnection {
            override fun onServiceConnected(p0: ComponentName?, p1: IBinder) {
                Log.d(TAG, "onServiceConnection")
                service = (p1 as ServiceForMusic.BinderMusic).service
                Log.d(TAG, "service = $service")


            }

            override fun onServiceDisconnected(p0: ComponentName?) {
                Log.d(TAG, "onServiceDisconnection")
            }

        }
        startService(Intent(baseContext, ServiceForMusic::class.java))

        bindService(
            Intent(baseContext, ServiceForMusic::class.java),
            sCon, 0
        )

    }

    override fun onDestroy() {
        Log.d(TAG, "MainActivity onDestroy")

        unbindService(sCon)
        super.onDestroy()
    }



}