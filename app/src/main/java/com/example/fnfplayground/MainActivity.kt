package com.example.fnfplayground

import android.content.ComponentName
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import com.example.fnfplayground.services.ServiceForMusic
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

class MainActivity : AppCompatActivity() {
    lateinit var service: ServiceForMusic
    private lateinit var sCon : ServiceConnection
    private lateinit var mAdView : AdView



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sCon = object: ServiceConnection {
            override fun onServiceConnected(p0: ComponentName?, p1: IBinder) {
                service = (p1 as ServiceForMusic.BinderMusic).service
            }

            override fun onServiceDisconnected(p0: ComponentName?) {
            }

        }
        startService(Intent(baseContext, ServiceForMusic::class.java))

        bindService(
            Intent(baseContext, ServiceForMusic::class.java),
            sCon, BIND_AUTO_CREATE
        )
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

    override fun onDestroy() {
        unbindService(sCon)
        stopService(Intent(this, ServiceForMusic::class.java))
        super.onDestroy()
    }



}