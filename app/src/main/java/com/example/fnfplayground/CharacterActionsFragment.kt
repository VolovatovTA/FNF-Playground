package com.example.fnfplayground

import android.annotation.SuppressLint
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


import android.content.res.AssetManager
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import java.io.IOException


private const val ARG_CHARACTER = "character name"


class CharacterActionsFragment : Fragment(), View.OnTouchListener {
    private var character: String? = null

    val sp = SoundPool(2, AudioManager.STREAM_MUSIC, 1)
    var idB by Delegates.notNull<Int>()
    var idLeft by Delegates.notNull<Int>()
    var idDown by Delegates.notNull<Int>()
    var idUp by Delegates.notNull<Int>()
    var idRight by Delegates.notNull<Int>()
    private lateinit var soundsId: Array<Int>
    private lateinit var currentAnimation: AnimationDrawable
    private lateinit var animations: MutableMap<String, AnimationDrawable>
    private lateinit var sortedList: MutableMap<String, String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            character = it.getString(ARG_CHARACTER)
        }
    }

    lateinit var binding: FragmentCharacterActionsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterActionsBinding.inflate(inflater, container, false)

        animations = mutableMapOf()

        // 0 - idle
        // 1 - spec
        // 2 - left
        // 3 - right
        // 4 - up
        // 5 - down
//        Log.d("DebugAnimation",
//            "images/characters/$character"
//        )
//        for (i in requireContext().assets.list("images/characters/$character")!!){
//            Log.d("DebugAnimation",
//                i
//            )
//        }


        val listOfActions =
            requireContext().assets.list("images/characters/$character") as Array<String>

        sortedList = sortList(listOfActions)
        for (i in sortedList){
            Log.d("DebugAnimation",
                "f = ${i.toPair().first}; s = ${i.toPair().second}"
            )
        }



        for (nameAction in sortedList) {
            // массив имён картинок для одной анимации
            val fileNamesForOneAnim =
                requireContext().assets.list("images/characters/$character/${nameAction.toPair().second}")
            animations[nameAction.toPair().second] = AnimationDrawable()
            animations[nameAction.toPair().second]?.isOneShot = false
            Log.d("DebugAnimation",
                "anim = $animations"
            )
            Log.d("DebugAnimation",
                "path = images/characters/$character/${nameAction.toPair().second}"
            )
            if (fileNamesForOneAnim != null) {
                for (fileName in fileNamesForOneAnim) {
                    Log.d("DebugAnimation",
                        "fileName = $fileName"
                    )
                    val inputStream = requireContext().assets
                        .open("images/characters/$character/${nameAction.toPair().second}/$fileName")
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    animations[nameAction.toPair().second]?.addFrame(
                        BitmapDrawable(resources, bitmap)
                        , 30
                    )
                }

            }
        }



        currentAnimation = animations[sortedList["idle"]]?:
        animations[sortedList["spec"]]?:
                AnimationDrawable()
        Log.d("DebugAnimation", "currentAnimation.numberOfFrames = ${currentAnimation.numberOfFrames}")
        return binding.root
    }

    private fun sortList(list: Array<String>) : MutableMap<String, String> {
        // 0 - idle
        // 1 - spec
        // 2 - left
        // 3 - right
        // 4 - up
        // 5 - down

        val sortedList : MutableMap<String, String> = HashMap(list.size)
        for (i in list) {
            if (i.contains("idle") || i.contains("IDLE") || i.contains("Idle")) {
                sortedList["idle"]= i
                Log.d("DebugAnimation", i)

            }
            if (i.contains("spec") || i.contains("SPEC") || i.contains("Spec")) {
                sortedList["spec"] = i
                Log.d("DebugAnimation", i)

            }
            if (i.contains("left") || i.contains("LEFT") || i.contains("Left")) {
                sortedList["left"] = i
            }
            if (i.contains("right") || i.contains("RIGHT") || i.contains("Right")) {
                sortedList["right"] = i
            }
            if (i.contains("up") || i.contains("UP") || i.contains("Up")) {
                sortedList["up"] = i
            }
            if (i.contains("down") || i.contains("DOWN") || i.contains("Down")) {
                sortedList["down"] = i
            }

        }

        return sortedList

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
        binding.CharacterImageView.background = currentAnimation
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
        fun newInstance(param1: String) =
            CharacterActionsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_CHARACTER, param1)
                }
            }
    }


    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        when (p0!!.id) {
            R.id.imageButtonLeft -> {
                when (p1!!.action) {
                    MotionEvent.ACTION_DOWN -> {
                        sp.play(idLeft, 1F, 1F, 1, 0, 1F)
                        changeAnimation(animations[sortedList["left"]])
                    }
                    MotionEvent.ACTION_UP -> {
                        changeAnimation(animations[sortedList["idle"]])
                    }
                }
            }
            R.id.imageButtonDown -> {
                when (p1!!.action) {
                    MotionEvent.ACTION_DOWN -> {
                        sp.play(idDown, 1F, 1F, 1, 0, 1F)
                        changeAnimation(animations[sortedList["down"]])
                    }
                    MotionEvent.ACTION_UP -> {
                        changeAnimation(animations[sortedList["idle"]])
                    }
                }
            }
            R.id.imageButtonRight -> {
                when (p1!!.action) {
                    MotionEvent.ACTION_DOWN -> {
                        sp.play(idRight, 1F, 1F, 1, 0, 1F)
                        changeAnimation(animations[sortedList["right"]])
                    }
                    MotionEvent.ACTION_UP -> {
                        changeAnimation(animations[sortedList["idle"]])
                    }
                }
            }
            R.id.imageButtonUp -> {
                when (p1!!.action) {
                    MotionEvent.ACTION_DOWN -> {
                        sp.play(idUp, 1F, 1F, 1, 0, 1F)
                        changeAnimation(animations[sortedList["up"]])
                    }
                    MotionEvent.ACTION_UP -> {
                        changeAnimation(animations[sortedList["idle"]])
                    }
                }
            }
            R.id.imageButtonB -> {
                when (p1!!.action) {
                    MotionEvent.ACTION_DOWN -> {
                        sp.play(idB, 1F, 1F, 1, 0, 1F)
                        changeAnimation(animations[sortedList["spec"]])
                    }
                    MotionEvent.ACTION_UP -> {
                        changeAnimation(animations[sortedList["idle"]])
                    }
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

    private fun changeAnimation(newDrawable: Drawable?) {
        if (newDrawable != null){
            currentAnimation.stop()
            binding.CharacterImageView.background = newDrawable
            currentAnimation = newDrawable as AnimationDrawable
            currentAnimation.start()
        }

    }
}