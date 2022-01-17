package com.example.fnfplayground.fragments

import android.content.Context
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fnfplayground.adapters.ModCharactersIconsAdapter
import com.example.fnfplayground.adapters.OfficialCharactersIconsAdapter
import com.example.fnfplayground.R
import com.example.fnfplayground.config.Prefs
import com.example.fnfplayground.databinding.FragmentFullscreenChooseCharacterBinding


class FullscreenFragmentChooseCharacter : Fragment() {


    private var fullscreenContent: View? = null
    private var fullscreenContentControls: View? = null
    val soundPool = SoundPool(4, AudioManager.STREAM_MUSIC, 100)
    var idSound = 0
    private var _binding: FragmentFullscreenChooseCharacterBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFullscreenChooseCharacterBinding
            .inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        idSound = soundPool.load(requireContext(), R.raw.scroll_menu, 1)
        val adapterCharacters = OfficialCharactersIconsAdapter(requireContext())
        val gridViewCharacters = binding.coverFlowOfficialCharacters
        gridViewCharacters.adapter = adapterCharacters

        val volume = requireActivity()
            .getSharedPreferences(
                Prefs.APP_PREFERENCES,
                Context.MODE_PRIVATE
            )
            .getFloat(
                Prefs.APP_PREFERENCES_VOLUME_SOUNDS_BUTTONS,
                1f
            )
        _binding?.imageButtonBack?.setOnClickListener {
            soundPool.play(idSound, volume, volume, 1, 0, 1f)
            requireActivity().onBackPressed()
        }
        gridViewCharacters.setOnItemClickListener { _, _, position, _ ->
            soundPool.play(idSound, volume, volume, 1, 0, 1f)

            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            Log.d(
                "DebugAnimation", "name of char = ${
                    adapterCharacters
                        .arrayListCharactersFolders[position].split(".")[0]
                }"
            )

            transaction.replace(
                R.id.fragmentContainerView2, CharacterActionsFragment.newInstance(
                    adapterCharacters.arrayListCharactersFolders[position]
                        .split(".")[0], false
                )
            )

            transaction.addToBackStack("1")
            transaction.commit()
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        fullscreenContent = null
        fullscreenContentControls = null
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}