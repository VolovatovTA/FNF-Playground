package com.example.fnfplayground

import android.annotation.SuppressLint
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.example.fnfplayground.databinding.FragmentCharacterActionsBinding
import java.util.*
import kotlin.properties.Delegates

private const val ARG_CHARACTER = "character id"


class CharacterActionsFragment : Fragment(), View.OnTouchListener {
    private var character: Character? = null
    val sp = SoundPool(2, AudioManager.STREAM_MUSIC, 1)
    var idB by Delegates.notNull<Int>()
    var idLeft by Delegates.notNull<Int>()
    var idDown by Delegates.notNull<Int>()
    var idUp by Delegates.notNull<Int>()
    var idRight by Delegates.notNull<Int>()
    lateinit var currentAnimation: AnimationDrawable
    lateinit var drawableIdle: Drawable
    lateinit var drawableLeft: Drawable
    lateinit var drawableRight: Drawable
    lateinit var drawableUp: Drawable
    lateinit var drawableDown: Drawable
    lateinit var drawableB: Drawable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            character = it.getParcelable(ARG_CHARACTER)
        }
    }
    lateinit var binding: FragmentCharacterActionsBinding

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharacterActionsBinding.inflate(inflater, container, false)

        drawableIdle = resources.getDrawable(R.drawable.anim_boyfriend_idle)
        drawableLeft = resources.getDrawable(R.drawable.anim_boyfriend_left)
        drawableDown = resources.getDrawable(R.drawable.anim_boyfriend_down)
        drawableUp = resources.getDrawable(R.drawable.anim_boyfriend_up)
        drawableRight = resources.getDrawable(R.drawable.anim_boyfriend_right)
        drawableB = resources.getDrawable(R.drawable.anim_boyfriend_b)
        currentAnimation = drawableIdle as AnimationDrawable



        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.CharacterImageView.background = drawableIdle
        currentAnimation.start()
        binding.imageButtonLeft.setOnTouchListener(this)
        binding.imageButtonRight.setOnTouchListener(this)
        binding.imageButtonDown.setOnTouchListener(this)
        binding.imageButtonUp.setOnTouchListener(this)
        binding.imageButtonB.setOnTouchListener(this)

        idB = sp.load(requireContext(), R.raw.b, 1)
        idLeft = sp.load(requireContext(), R.raw.left, 1)
        idRight = sp.load(requireContext(), R.raw.right, 1)
        idUp = sp.load(requireContext(), R.raw.up, 1)
        idDown = sp.load(requireContext(), R.raw.down, 1)




    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Character) =
            CharacterActionsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_CHARACTER, param1)
                }
            }
    }




    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        when (p0!!.id) {
            R.id.imageButtonLeft -> {
                when (p1!!.action) {
                    MotionEvent.ACTION_DOWN -> {
                        sp.play(idLeft, 1F, 1F, 1, 0, 1F)
                        changeAnimation(drawableLeft)
                    }
                    MotionEvent.ACTION_UP -> {changeAnimation(drawableIdle)}
                }
            }
            R.id.imageButtonDown -> {
                when (p1!!.action) {
                    MotionEvent.ACTION_DOWN -> {
                        sp.play(idDown, 1F, 1F, 1, 0, 1F)
                        changeAnimation(drawableDown)
                    }
                    MotionEvent.ACTION_UP -> {changeAnimation(drawableIdle)}
                }
            }
            R.id.imageButtonRight -> {
                when (p1!!.action) {
                    MotionEvent.ACTION_DOWN -> {
                        sp.play(idRight, 1F, 1F, 1, 0, 1F)
                        changeAnimation(drawableRight)
                    }
                    MotionEvent.ACTION_UP -> {changeAnimation(drawableIdle)}
                }
            }
            R.id.imageButtonUp -> {
                when (p1!!.action) {
                    MotionEvent.ACTION_DOWN -> {
                        sp.play(idUp, 1F, 1F, 1, 0, 1F)
                        changeAnimation(drawableUp)
                    }
                    MotionEvent.ACTION_UP -> {changeAnimation(drawableIdle)}
                }
            }
            R.id.imageButtonB -> {
                when (p1!!.action) {
                    MotionEvent.ACTION_DOWN -> {
                        sp.play(idB, 1F, 1F, 1, 0, 1F)
                        changeAnimation(drawableB)
                    }
                    MotionEvent.ACTION_UP -> {changeAnimation(drawableIdle)}
                }
            }
        }

//        when (p0!!.id) {
//            R.id.imageButtonLeft -> {
//                currentAnimation.stop()
//                currentAnimation = drawableLeft as AnimationDrawable
//                currentAnimation.start()
//                sp.play(idLeft, 1F, 1F, 1, 0, 1F)
//            }
//            R.id.imageButtonRight -> {
//
//                sp.play(idRight, 1F, 1F, 1, 0, 1F)
//            }
//            R.id.imageButtonUp -> {
//
//                sp.play(idUp, 1F, 1F, 1, 0, 1F)
//            }
//            R.id.imageButtonDown -> {
//
//                sp.play(idDown, 1F, 1F, 1, 0, 1F)
//            }
//            R.id.imageButton2 -> {
//
//                sp.play(idB, 1F, 1F, 1, 0, 1F)
//            }
//
//
//        }
//        currentAnimation.stop()
//        currentAnimation = drawableIdle as AnimationDrawable
//        currentAnimation.start()
        return true
    }

    private fun changeAnimation(newDrawable: Drawable) {
        currentAnimation.stop()
        binding.CharacterImageView.background = newDrawable
        currentAnimation = newDrawable as AnimationDrawable
        currentAnimation.start()    }
}