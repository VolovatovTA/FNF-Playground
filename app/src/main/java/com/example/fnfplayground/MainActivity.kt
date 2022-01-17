package com.example.fnfplayground

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

class MainActivity : AppCompatActivity() {
    val TAG = "DebugAnimation"
    lateinit var viewModel: MyViewModel
    private lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MyViewModel::class.java]
        viewModel.playMusicIfItNotPlaying(application)
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)


    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.d(TAG, "onAttachedToWindow")
    }

    override fun onResume() {
        Log.d(TAG, "onResume")
        viewModel.playMusicIfItNotPlaying(application)

        super.onResume()
    }


    override fun onUserLeaveHint() {
        Log.d(TAG, "onUserLeaveHint")

        viewModel.pauseMusic()

        super.onUserLeaveHint()
    }


    private var exit = false

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount == 0){
            if (exit) {

                viewModel.pauseMusic()

                finish() // finish activity
            } else {
                Toast.makeText(
                    this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT
                ).show()
                exit = true
                Handler().postDelayed( { exit = false }, 3 * 1000)
            }
        }
        else super.onBackPressed()

    }



}