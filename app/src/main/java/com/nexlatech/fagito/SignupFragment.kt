package com.nexlatech.fagito

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.nexlatech.fagito.api.FagitoService
import com.nexlatech.fagito.api.Resource
import com.nexlatech.fagito.api.RetrofitHelper
import com.nexlatech.fagito.databinding.FragmentSignupBinding
import com.nexlatech.fagito.repository.FagitoRepository
import com.nexlatech.fagito.viewmodel.MainViewModel
import com.nexlatech.fagito.viewmodel.MainViewModelFactory


class SignupFragment : Fragment() {
    private var _binding: FragmentSignupBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        val view = binding.root

        //mainViewModel setup
        val fagitoService = RetrofitHelper.getInstance().create(FagitoService::class.java)
        val repository = FagitoRepository(fagitoService, requireContext())
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository))[MainViewModel::class.java]



        binding.acbSignUp.setOnClickListener {
            val email = binding.tiEmail.text.toString()
            val userName = binding.tiUsername.text.toString()
            val password = binding.tiPassword.text.toString()
            val firstName = binding.tiFirstName.text.toString()
            val lastName = binding.tiLastName.text.toString()

            mainViewModel.signUp(email,userName,password,firstName,lastName)

            mainViewModel.signUpMVM.observe(viewLifecycleOwner, Observer { it ->
                when(it){
                    is Resource.DoNothing -> {
                    }
                    is Resource.Success -> {
                        Log.d("println",it.value.postRequest.content.accessToken)

                        saveToSharedPreferences(
                            "jsonTokenKey", it.value.postRequest.content.accessToken)

                        Snackbar.make(view,"Account Created successfully.", 5000).show()

                        Navigation.findNavController(view).navigate(R.id.action_signupFragment_to_signupAllergyFragment)
                    }
                    is Resource.Failure -> {
                        Snackbar.make(view,"Error has occurred", 5000).show()
                    }
                    else -> {}
                }
            })


        }

        return view
    }

    private fun saveToSharedPreferences(key: String, token: String){
        //saving jsonToken to shared Preferences

        val sharedPref = activity?.getSharedPreferences("jsonTokenFile", Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString(key, token)
            apply()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}