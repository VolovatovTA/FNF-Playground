package com.example.fnfplayground

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.get
import com.example.fnfplayground.databinding.FragmentCharacterActionsBinding
import kotlin.properties.Delegates
import android.widget.Toast


import android.content.res.AssetManager
import java.io.IOException


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
    lateinit var drawable: MutableMap<String, Drawable>
    val listOfActions = listOf("left", "right", "up", "down", "b", "idle" )



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

        drawable = mutableMapOf()
//        for (nameAction in listOfActions){
//            drawable[nameAction] = resources.getDrawable(resources.getIdentifier("anim_boyfriend_$nameAction", "", "drawable"))
//
//        }
//        val myAssetManager: AssetManager =
//            requireContext().getAssets()
//
//        try {
//            val Files = myAssetManager.list("") // массив имён файлов
//            Log.d("DebugAnimation", Files!![0].toString() + Files[1].toString())
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//        Log.d("DebugAnimation", (requireContext().assets.open("1")).bufferedReader().use {
//            it.readText()})
//        drawable["idle"] = Drawable.createFromStream(requireContext().assets.open("anim_boyfriend_idle"), null)
        drawable["idle"] = resources.getDrawable(R.drawable.anim_boyfriend_idle)

        drawable["left"] = resources.getDrawable(R.drawable.anim_boyfriend_left)
        drawable["right"] = resources.getDrawable(R.drawable.anim_boyfriend_right)
        drawable["up"] = resources.getDrawable(R.drawable.anim_boyfriend_up)
        drawable["down"] = resources.getDrawable(R.drawable.anim_boyfriend_down)
        drawable["b"] = resources.getDrawable(R.drawable.anim_boyfriend_b)

        currentAnimation = drawable["idle"] as AnimationDrawable
//        val d1 = resources.getDrawable(R.drawable.boyfriend_down_00)
//        val d2 = resources.getDrawable(R.drawable.down01)
//        compare2drawable(d1, d2)

        return binding.root
    }

    private fun compare2drawable(d1: Drawable, d2: Drawable) {


        val r = Runnable {
            var dif : Array<Array<Int>> = emptyArray()

            for(i in 0 until d1.toBitmap().width){
                var array = arrayOf<Int>()
                for (j in 0 until d1.toBitmap().height){
                    array += d1.toBitmap()[i,j] - d2.toBitmap()[i,j]
                }
                dif += array
            }
            Log.d("DebugAnimation", "calculate")

            var res = ""
            for(i in 0 until d1.toBitmap().width){
                var res1 = ""
                for (j in 0 until d1.toBitmap().height){
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
        binding.CharacterImageView.background = drawable["idle"]
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
                        changeAnimation(drawable["left"]!!)
                    }
                    MotionEvent.ACTION_UP -> {changeAnimation(drawable["idle"]!!)}
                }
            }
            R.id.imageButtonDown -> {
                when (p1!!.action) {
                    MotionEvent.ACTION_DOWN -> {
                        sp.play(idDown, 1F, 1F, 1, 0, 1F)
                        changeAnimation(drawable["down"]!!)
                    }
                    MotionEvent.ACTION_UP -> {changeAnimation(drawable["idle"]!!)}
                }
            }
            R.id.imageButtonRight -> {
                when (p1!!.action) {
                    MotionEvent.ACTION_DOWN -> {
                        sp.play(idRight, 1F, 1F, 1, 0, 1F)
                        changeAnimation(drawable["right"]!!)
                    }
                    MotionEvent.ACTION_UP -> {changeAnimation(drawable["idle"]!!)}
                }
            }
            R.id.imageButtonUp -> {
                when (p1!!.action) {
                    MotionEvent.ACTION_DOWN -> {
                        sp.play(idUp, 1F, 1F, 1, 0, 1F)
                        changeAnimation(drawable["up"]!!)
                    }
                    MotionEvent.ACTION_UP -> {changeAnimation(drawable["idle"]!!)}
                }
            }
            R.id.imageButtonB -> {
                when (p1!!.action) {
                    MotionEvent.ACTION_DOWN -> {
                        sp.play(idB, 1F, 1F, 1, 0, 1F)
                        changeAnimation(drawable["b"]!!)
                    }
                    MotionEvent.ACTION_UP -> {changeAnimation(drawable["idle"]!!)}
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