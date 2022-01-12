package com.example.fnfplayground

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fnfplayground.databinding.FragmentCharacterActionsBinding
import com.example.fnfplayground.databinding.FragmentFullscreenChooseCharacterBinding
import java.util.*

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


        val myAdapter = CharactersImageAdapter(requireContext())

        val mCoverFlow = binding.coverFlowOfficialCharacters
        val mCoverFlow2 = binding.coverFlowModCharacters
        mCoverFlow.adapter = myAdapter
        mCoverFlow2.adapter = myAdapter
        mCoverFlow.setReflectionOpacity(0)

        mCoverFlow.setOnItemClickListener { parent, view, position, id ->
//            Toast.makeText(requireContext(), "pick on $position", Toast.LENGTH_SHORT).show()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            Log.d("DebugAnimation", myAdapter.arrayListCharactersImage[position].split(".")[0])

            transaction.replace(R.id.fragmentContainerView2, CharacterActionsFragment.newInstance(myAdapter.arrayListCharactersImage[position].split(".")[0]))

            transaction.addToBackStack("1")
            transaction.commit()
        }
        mCoverFlow.setOnScrollPositionListener(object : OnScrollPositionListener {
            override fun onScrolledToPosition(position: Int) {
//                Toast.makeText(requireContext(), "scrolled pos = $position", Toast.LENGTH_SHORT).show()
            }

            override fun onScrolling() {

//                Toast.makeText(requireContext(), "scrolling", Toast.LENGTH_SHORT).show()
            }
        })


    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()

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