package com.example.fnfplayground.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fnfplayground.databinding.FragmentInfoBinding

class InfoFragment : Fragment() {

    lateinit var binding: FragmentInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoBinding.inflate(inflater, container, false)

        binding.imageButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
        return binding.root
    }

}