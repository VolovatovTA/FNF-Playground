package com.example.fnfplayground

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.view.WindowManager
import android.content.Intent




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