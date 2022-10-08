package com.nexlatech.fagito

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.nexlatech.fagito.databinding.FragmentStartupScreenBinding


class StartupScreen : Fragment() {
    private var _binding: FragmentStartupScreenBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartupScreenBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.textView.setOnClickListener {
            val action = StartupScreenDirections.actionStartupScreenToAskingSignupLogin("jayesh")

            Navigation.findNavController(view).navigate(action)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}