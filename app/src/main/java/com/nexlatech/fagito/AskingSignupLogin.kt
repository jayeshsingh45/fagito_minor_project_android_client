package com.nexlatech.fagito

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.nexlatech.fagito.databinding.FragmentAskingSignupLoginBinding

class AskingSignupLogin : Fragment() {
    private var _binding: FragmentAskingSignupLoginBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAskingSignupLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        //step 1 create bundle
        val bundle = arguments

        if (bundle == null){
            Log.d("Confiration", "Fragment AskingSignupLogin didn't receive any info.")

        }

        //step 2 extract args from bundle
        val args = bundle?.let { AskingSignupLoginArgs.fromBundle(it) }

        if(args?.number.isNullOrBlank()){
            binding.textView2.text = "no value got here"
        }else{
            binding.textView2.text = args?.number
        }




        binding.textView2.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_askingSignupLogin_to_startupScreen)
        }

        return view
    }
}