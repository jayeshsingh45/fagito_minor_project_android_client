package com.nexlatech.fagito

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nexlatech.fagito.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        val jsonToken = getJsonTokenSharedPreferences()

        Log.d("println", "jsonToken Shared preferences " + jsonToken.toString())


        return view
    }

    private fun getJsonTokenSharedPreferences():String? {
        //getting Json token from shared preferences.

        val sharedPreference = activity?.getSharedPreferences("jsonTokenFile",Context.MODE_PRIVATE)
        val jsonToken = sharedPreference?.getString("jsonTokenKey","NoToken");

        return jsonToken;
    }


}