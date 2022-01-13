package com.example.fnfplayground

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.example.fnfplayground.services.ServiceForMusic


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startService(Intent(this, ServiceForMusic::class.java)) //вот єто вам нужно написать!!!!!!


    }

    override fun onDestroy() {
        stopService(Intent(this, ServiceForMusic::class.java))
        super.onDestroy()
    }



}