package com.example.fnfplayground

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fnfplayground.databinding.FragmentFullscreenChooseCharacterBinding
import java.util.*
import kotlin.concurrent.fixedRateTimer


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
        val gridView = _binding?.GridCharacters
        gridView?.adapter = myAdapter
        gridView?.setOnItemClickListener { _, _, position, _ ->
//            Toast.makeText(requireContext(), "pick on $position", Toast.LENGTH_SHORT).show()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(
                R.id.fragmentContainerView, CharacterActionsFragment
                    .newInstance(
                        Character(
                            UUID(1, 2), -1, -1,
                            -1, -1, -1, -1, -1,
                            -1, -1, -1
                        )
                    )
            )
            transaction.addToBackStack("1")
            transaction.commit()


        }

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