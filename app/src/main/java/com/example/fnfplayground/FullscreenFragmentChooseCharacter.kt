package com.example.fnfplayground

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fnfplayground.databinding.FragmentFullscreenChooseCharacterBinding

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow.OnScrollPositionListener


class FullscreenFragmentChooseCharacter : Fragment() {


    private var fullscreenContent: View? = null
    private var fullscreenContentControls: View? = null

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


        val adapterOfficialCharacters = OfficialCharactersIconsAdapter(requireContext())
        val adapterModCharacters = ModCharactersIconsAdapter(requireContext())

        val coverFlowOfficialCharacter = binding.coverFlowOfficialCharacters
        val coverFlowModCharacter = binding.coverFlowModCharacters

        coverFlowOfficialCharacter.adapter = adapterOfficialCharacters
        coverFlowModCharacter.adapter = adapterModCharacters

        coverFlowOfficialCharacter.setReflectionOpacity(0)

        coverFlowOfficialCharacter.setOnItemClickListener { _, _, position, _ ->

            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            Log.d("DebugAnimation", "name of char = ${adapterOfficialCharacters
                .arrayListCharactersFolders[position].split(".")[0]}")

            transaction.replace(R.id.fragmentContainerView2, CharacterActionsFragment
                .newInstance(adapterOfficialCharacters.arrayListCharactersFolders[position]
                    .split(".")[0], false))

            transaction.addToBackStack("1")
            transaction.commit()
        }
        coverFlowModCharacter.setOnItemClickListener { _, _, position, _ ->

            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            Log.d("DebugAnimation", "name of char = ${adapterModCharacters
                .arrayListCharactersFolders[position].split(".")[0]}")

            transaction.replace(R.id.fragmentContainerView2, CharacterActionsFragment
                .newInstance(adapterModCharacters.arrayListCharactersFolders[position]
                    .split(".")[0], true))

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