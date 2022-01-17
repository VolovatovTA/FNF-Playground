package com.example.fnfplayground

import android.content.ComponentName
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.ServiceConnection
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.fnfplayground.services.ServiceForMusic
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

class MainActivity : AppCompatActivity() {
    val TAG = "DebugAnimation"
    lateinit var viewModel: MyViewModel
    private lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MyViewModel::class.java]
        viewModel.playMusic()
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        val loadAd = mAdView.loadAd(adRequest)
        mAdView.adListener = object: AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdFailedToLoad(adError : LoadAdError) {
                // Code to be executed when an ad request fails.
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        }
    }

    private var exit = false
    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount == 0){
            if (exit) {
                viewModel.service?.player?.stop()

                viewModel.service?.stopService(viewModel.intent_)

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