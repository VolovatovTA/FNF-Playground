package com.example.fnfplayground.fragments

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.fnfplayground.R
import com.example.fnfplayground.databinding.FragmentSettingsBinding
import com.example.fnfplayground.services.ServiceForMusic


class SettingsFragment : Fragment(), SeekBar.OnSeekBarChangeListener {
    lateinit var binding: FragmentSettingsBinding

    lateinit var service: ServiceForMusic
    lateinit var sCon: ServiceConnection
    val soundPool = SoundPool(4, AudioManager.STREAM_MUSIC, 100)
    var idSound = 0
    val APP_PREFERENCES = "mySettings"
    val APP_PREFERENCES_VOLUME_SOUNDS = "volumeSounds"
    val APP_PREFERENCES_VOLUME_MUSIC = "volumeMusic"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        idSound = soundPool.load(requireContext(), R.raw.scroll_menu, 1)

        sCon = object: ServiceConnection {
            override fun onServiceConnected(p0: ComponentName?, p1: IBinder) {
                service = (p1 as ServiceForMusic.BinderMusic).service

            }

            override fun onServiceDisconnected(p0: ComponentName?) {
            }

        }

        activity?.bindService(
            Intent(requireContext(), ServiceForMusic::class.java),
            sCon, AppCompatActivity.BIND_AUTO_CREATE
        )

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        val settings = requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)


        binding.seekBarMusicVolume.progress = (settings.getFloat(APP_PREFERENCES_VOLUME_MUSIC, 30f)*100).toInt()
        binding.seekBarSoundsVolume.progress = (settings.getFloat(APP_PREFERENCES_VOLUME_SOUNDS, 10f)*10).toInt()


        binding.seekBarMusicVolume.setOnSeekBarChangeListener(this)
        binding.seekBarSoundsVolume.setOnSeekBarChangeListener(this)

        binding.imageButtonBack.setOnClickListener {
            soundPool.play(
                idSound,
                binding.seekBarSoundsVolume.progress.toFloat()/10,
                binding.seekBarSoundsVolume.progress.toFloat()/10,
                1,
                0,
                1f
            )

            val editor = settings.edit()
            editor.putFloat(APP_PREFERENCES_VOLUME_MUSIC, binding.seekBarMusicVolume.progress.toFloat()/100)
            editor.putFloat(APP_PREFERENCES_VOLUME_SOUNDS, binding.seekBarSoundsVolume.progress.toFloat()/10)
            editor.apply()


            activity?.onBackPressed()
        }
        return binding.root
    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

        when (p0?.id){
            R.id.seekBarMusicVolume ->{
                service.player?.setVolume(p1 / 100f, p1 / 100f)
            }
            R.id.seekBarSoundsVolume -> {
                val volume = p0.progress.toFloat()/p0.max

                soundPool.play(idSound, volume, volume, 1, 0, 1f)

            }
        }

    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
    }

    override fun onDestroy() {
        super.onDestroy()

        requireActivity().unbindService(sCon)
    }

}