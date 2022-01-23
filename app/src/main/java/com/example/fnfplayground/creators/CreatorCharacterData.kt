package com.example.fnfplayground.creators

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.BitmapDrawable
import android.media.AudioManager
import android.media.SoundPool
import android.util.Log

class CreatorCharacterData(
    val context: Context,
    private val character: String,
    val isItMod: Boolean
) {
    var FILE_PATH_TO_ANIMATIONS: String

    val FILE_PATH_TO_MOD_ANIMATIONS = "shared/mod characters/"
    val FILE_PATH_TO_OFFICIAL_ANIMATIONS = "shared/official characters/"
    private val DEFAULT_DURATION = 30 // ms

    val sp = SoundPool(2, AudioManager.STREAM_MUSIC, 1)

    var animations: MutableMap<ActionsCharacter, AnimationDrawable> = mutableMapOf()
    var soundsId: MutableMap<ActionsCharacter, Int> = mutableMapOf()
    private var sortedListAnimations: MutableMap<ActionsCharacter, String>
    private var sortedListSounds: MutableMap<ActionsCharacter, String>

    init {
        FILE_PATH_TO_ANIMATIONS = if (isItMod) FILE_PATH_TO_MOD_ANIMATIONS else FILE_PATH_TO_OFFICIAL_ANIMATIONS

        val listOfNamesFilesInAnimations =
            context.assets.list("$FILE_PATH_TO_ANIMATIONS$character/animations") as Array<String>
        sortedListAnimations = sortList(listOfNamesFilesInAnimations)

        val listOfNamesFilesInSounds =
            context.assets.list("$FILE_PATH_TO_ANIMATIONS$character/sounds") as Array<String>
        sortedListSounds = sortList(listOfNamesFilesInSounds)

        for (s in sortedListSounds.keys){
            soundsId[s] = sp.load(context.assets.openFd("$FILE_PATH_TO_ANIMATIONS$character/sounds/${sortedListSounds[s]}"), 1)
        }


        for (nameAction in sortedListAnimations.keys) {
            // массив имён картинок для одной анимации
            val fileNamesForOneAnim =
                context.assets.list("$FILE_PATH_TO_ANIMATIONS$character/animations/${sortedListAnimations[nameAction]}")
            animations[nameAction] = AnimationDrawable()
            animations[nameAction]?.isOneShot = nameAction != ActionsCharacter.IDLE

            if (fileNamesForOneAnim != null) {
                var previousNumberOfPhoto = fileNamesForOneAnim[0]
                for (fileName in fileNamesForOneAnim) {

                    val duration_coefficiemt = fileName.split(".")[0].takeLast(3)
                        .toInt() - previousNumberOfPhoto.split(".")[0].takeLast(3).toInt()

                    val inputStream = context.assets
                        .open("$FILE_PATH_TO_ANIMATIONS$character/animations/${sortedListAnimations[nameAction]}/$fileName")
                    val bitmap = BitmapFactory.decodeStream(inputStream)

                    animations[nameAction]?.addFrame(
                        BitmapDrawable(context.resources, bitmap)
                        , DEFAULT_DURATION*duration_coefficiemt
                    )
                    previousNumberOfPhoto = fileName
                }

            }
        }
    }

    private fun sortList(list: Array<String>) : MutableMap<ActionsCharacter, String> {

        val sortedList : MutableMap<ActionsCharacter, String> = HashMap(list.size)
        for (i in list) {
            if (i.contains("idle") || i.contains("IDLE") || i.contains("Idle") || i.contains("I")) {
                sortedList[ActionsCharacter.IDLE]= i
                Log.d("DebugAnimation", i)

            }
            if (i.contains("spec") || i.contains("SPEC") || i.contains("Spec")|| i.contains("S")) {
                sortedList[ActionsCharacter.SPEC] = i
                Log.d("DebugAnimation", i)

            }
            if (i.contains("left") || i.contains("LEFT") || i.contains("Left")|| i.contains("L")) {
                sortedList[ActionsCharacter.LEFT] = i
            }
            if (i.contains("right") || i.contains("RIGHT") || i.contains("Right")|| i.contains("R")) {
                sortedList[ActionsCharacter.RIGHT] = i
            }
            if (i.contains("up") || i.contains("UP") || i.contains("Up") || i.contains("U")) {
                sortedList[ActionsCharacter.UP] = i
            }
            if (i.contains("down") || i.contains("DOWN") || i.contains("Down")|| i.contains("D")) {
                sortedList[ActionsCharacter.DOWN] = i
            }

        }

        return sortedList

    }

    fun play(action: ActionsCharacter, volume: Float) {

        soundsId[action]?.let { sp.play(it, volume, volume, 1, 0, 1F) }
    }
}