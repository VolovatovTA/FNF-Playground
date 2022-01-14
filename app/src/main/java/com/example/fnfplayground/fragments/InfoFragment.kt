package com.example.fnfplayground.fragments

import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fnfplayground.R
import com.example.fnfplayground.databinding.FragmentInfoBinding

class InfoFragment : Fragment() {

    lateinit var binding: FragmentInfoBinding
    val soundPool = SoundPool(4, AudioManager.STREAM_MUSIC, 100)
    var idSound = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoBinding.inflate(inflater, container, false)
        idSound = soundPool.load(requireContext(), R.raw.scroll_menu, 1)

        binding.imageButton.setOnClickListener {

            soundPool.play(idSound, 1f, 1f, 1, 0, 1f)

            requireActivity().onBackPressed()
        }
        return binding.root
    }

}