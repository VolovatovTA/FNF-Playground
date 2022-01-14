package com.example.fnfplayground.fragments

import android.graphics.BitmapFactory
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.BitmapDrawable
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fnfplayground.R
import com.example.fnfplayground.databinding.FragmentIntroBinding


class IntroFragment : Fragment() {

    lateinit var binding: FragmentIntroBinding
    val soundPool = SoundPool(4, AudioManager.STREAM_MUSIC, 100)
    var idSound = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        idSound = soundPool.load(requireContext(), R.raw.scroll_menu, 1)
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentIntroBinding.inflate(inflater, container, false)
        val fileNamesForOneAnim =
            requireContext().assets.list("shared/logo bumpin")
        val animation = AnimationDrawable()
        animation.isOneShot = false
        if (fileNamesForOneAnim != null) {
            var previousNumberOfPhoto = fileNamesForOneAnim[0]

            for (i in fileNamesForOneAnim){
                val duration_coefficiemt = i.split(".")[0].takeLast(4).toInt() - previousNumberOfPhoto.split(".")[0].takeLast(4).toInt()

                val inputStream = requireContext().assets
                    .open("shared/logo bumpin/$i")
                val bitmap = BitmapFactory.decodeStream(inputStream)
                animation.addFrame(BitmapDrawable(resources, bitmap), 30*duration_coefficiemt)
                previousNumberOfPhoto = i

            }
        }
        binding.imageView.setImageDrawable(animation)
        animation.start()
        binding.button.setOnClickListener {
            soundPool.play(idSound, 1f, 1f, 1, 0, 1f)
            val transaction = requireActivity().supportFragmentManager.beginTransaction()

            transaction.replace(R.id.fragmentContainerView2, FullscreenFragmentChooseCharacter())

            transaction.addToBackStack("1")
            transaction.commit()
        }
        binding.button2.setOnClickListener {
            soundPool.play(idSound, 1f, 1f, 1, 0, 1f)

            val transaction = requireActivity().supportFragmentManager.beginTransaction()

            transaction.replace(R.id.fragmentContainerView2, InfoFragment())

            transaction.addToBackStack("1")
            transaction.commit()
        }


        return binding.root
    }


}