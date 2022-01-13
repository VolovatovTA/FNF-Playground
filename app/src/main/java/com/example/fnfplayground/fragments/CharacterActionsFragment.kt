package com.example.fnfplayground.fragments


import android.annotation.SuppressLint
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.get
import androidx.fragment.app.Fragment
import com.example.fnfplayground.creators.ActionsCharacter
import com.example.fnfplayground.creators.CreatorCharacterData
import com.example.fnfplayground.R
import com.example.fnfplayground.databinding.FragmentCharacterActionsBinding
import java.io.IOException


private const val ARG_CHARACTER = "character name"
private const val ARG_MODE = "mode flag"


class CharacterActionsFragment : Fragment(), View.OnTouchListener {
    private var character: String? = null
    private var mode: Boolean? = null
        private lateinit var currentAnimation: AnimationDrawable
    private lateinit var creatorCharacterData: CreatorCharacterData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            character = it.getString(ARG_CHARACTER)
            mode = it.getBoolean(ARG_MODE)
        }
    }

    lateinit var binding: FragmentCharacterActionsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterActionsBinding.inflate(inflater, container, false)

        creatorCharacterData = CreatorCharacterData(requireContext(), character!!, mode!!)

        currentAnimation = creatorCharacterData.animations[ActionsCharacter.IDLE]?:
        throw IOException("You can't create a character without idle animation")

        return binding.root
    }



    private fun compare2drawable(d1: Drawable, d2: Drawable) {


        val r = Runnable {
            var dif: Array<Array<Int>> = emptyArray()

            for (i in 0 until d1.toBitmap().width) {
                var array = arrayOf<Int>()
                for (j in 0 until d1.toBitmap().height) {
                    array += d1.toBitmap()[i, j] - d2.toBitmap()[i, j]
                }
                dif += array
            }
            Log.d("DebugAnimation", "calculate")

            var res = ""
            for (i in 0 until d1.toBitmap().width) {
                var res1 = ""
                for (j in 0 until d1.toBitmap().height) {
                    res1 += dif[i][j]
                }
                res += res1

                res += "\n"
            }
            Log.d("DebugAnimation", "res = $res")

        }
        val thread = Thread(r)

        thread.start()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.CharacterImageView.setImageDrawable(currentAnimation)
        currentAnimation.start()
        binding.imageButtonLeft.setOnTouchListener(this)
        binding.imageButtonRight.setOnTouchListener(this)
        binding.imageButtonDown.setOnTouchListener(this)
        binding.imageButtonUp.setOnTouchListener(this)
        binding.imageButtonB.setOnTouchListener(this)



    }

    companion object {
        @JvmStatic
        fun newInstance(nameCharacter: String, isItMode: Boolean) =
            CharacterActionsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_CHARACTER, nameCharacter)
                    putBoolean(ARG_MODE, isItMode)
                }
            }
    }


    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        p0!!.performClick()
        when (p0.id) {
            R.id.imageButtonLeft -> {
                when (p1!!.action) {
                    MotionEvent.ACTION_DOWN -> {
                        p0.isPressed = !p0.isPressed
                        creatorCharacterData.play(ActionsCharacter.LEFT)
                        changeAnimation(ActionsCharacter.LEFT)
                    }
                    MotionEvent.ACTION_UP -> {
                        p0.isPressed = !p0.isPressed

                        changeAnimation(ActionsCharacter.IDLE)
                    }
                }
            }
            R.id.imageButtonDown -> {
                when (p1!!.action) {
                    MotionEvent.ACTION_DOWN -> {
                        p0.isPressed = !p0.isPressed

                        creatorCharacterData.play(ActionsCharacter.DOWN)
                        changeAnimation(ActionsCharacter.DOWN)
                    }
                    MotionEvent.ACTION_UP -> {
                        p0.isPressed = !p0.isPressed

                        changeAnimation(ActionsCharacter.IDLE)
                    }
                }
            }
            R.id.imageButtonRight -> {
                when (p1!!.action) {
                    MotionEvent.ACTION_DOWN -> {
                        p0.isPressed = !p0.isPressed
                        creatorCharacterData.play(ActionsCharacter.RIGHT)
                        changeAnimation(ActionsCharacter.RIGHT)
                    }
                    MotionEvent.ACTION_UP -> {
                        p0.isPressed = !p0.isPressed
                        changeAnimation(ActionsCharacter.IDLE)
                    }
                }
            }
            R.id.imageButtonUp -> {
                when (p1!!.action) {
                    MotionEvent.ACTION_DOWN -> {
                        p0.isPressed = !p0.isPressed
                        creatorCharacterData.play(ActionsCharacter.UP)
                        changeAnimation(ActionsCharacter.UP)
                    }
                    MotionEvent.ACTION_UP -> {
                        p0.isPressed = !p0.isPressed
                        changeAnimation(ActionsCharacter.IDLE)
                    }
                }
            }
            R.id.imageButtonB -> {
                when (p1!!.action) {
                    MotionEvent.ACTION_DOWN -> {
                        p0.isPressed = !p0.isPressed
                        creatorCharacterData.play(ActionsCharacter.SPEC)
                        changeAnimation(ActionsCharacter.SPEC)
                    }
                    MotionEvent.ACTION_UP -> {
                        p0.isPressed = !p0.isPressed
                        changeAnimation(ActionsCharacter.IDLE)
                    }
                }
            }
        }

        return true
    }

    private fun changeAnimation(action: ActionsCharacter) {

        if (creatorCharacterData.animations[action] != null){
            currentAnimation.stop()
            currentAnimation = creatorCharacterData.animations[action]!!
            binding.CharacterImageView.setImageDrawable(currentAnimation)
            currentAnimation.start()
        }

    }
}