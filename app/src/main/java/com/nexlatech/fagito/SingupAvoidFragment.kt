package com.nexlatech.fagito

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.nexlatech.fagito.api.FagitoService
import com.nexlatech.fagito.api.Resource
import com.nexlatech.fagito.api.RetrofitHelper
import com.nexlatech.fagito.databinding.FragmentSingupAvoidBinding
import com.nexlatech.fagito.repository.FagitoRepository
import com.nexlatech.fagito.viewmodel.MainViewModel
import com.nexlatech.fagito.viewmodel.MainViewModelFactory


class SingupAvoidFragment : Fragment() {
    private var _binding: FragmentSingupAvoidBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSingupAvoidBinding.inflate(inflater, container, false)
        val view = binding.root

        //mainViewModel setup
        val fagitoService = RetrofitHelper.getInstance().create(FagitoService::class.java)
        val repository = FagitoRepository(fagitoService, requireContext())
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository))[MainViewModel::class.java]

        binding.buttonSubmit.setOnClickListener {
            if(binding.cbSugar.isChecked){

                signUpUserAvoid(11, "Sugar")

                mainViewModel.signUpUserAvoidMVM.observe(viewLifecycleOwner, Observer { it ->
                    when(it){
                        is Resource.DoNothing->{}
                        is Resource.Success ->{
                            Log.d("println",it.value.postRequest.description.toString())
                        }
                        is Resource.Failure ->{
                            Log.d("println", "Error in adding item to list")
                        }
                        else ->{}
                    }
                })

            }

            if(binding.cbOnion.isChecked){

                signUpUserAvoid(12, "Onion")

                mainViewModel.signUpUserAvoidMVM.observe(viewLifecycleOwner, Observer { it ->
                    when(it){
                        is Resource.DoNothing->{}
                        is Resource.Success ->{
                            Log.d("println",it.value.postRequest.description.toString())
                        }
                        is Resource.Failure ->{
                            Log.d("println", "Error in adding item to list")
                        }
                        else ->{}
                    }
                })

            }

            if(binding.cbPalmOil.isChecked){

                signUpUserAvoid(13, "Palm Oil")

                mainViewModel.signUpUserAvoidMVM.observe(viewLifecycleOwner, Observer { it ->
                    when(it){
                        is Resource.DoNothing->{}
                        is Resource.Success ->{
                            Log.d("println",it.value.postRequest.description.toString())
                        }
                        is Resource.Failure ->{
                            Log.d("println", "Error in adding item to list")
                        }
                        else ->{}
                    }
                })

            }

            if(binding.cbArtificialFlavours.isChecked){

                signUpUserAvoid(14, "Artificial Flavours")

                mainViewModel.signUpUserAvoidMVM.observe(viewLifecycleOwner, Observer { it ->
                    when(it){
                        is Resource.DoNothing->{}
                        is Resource.Success ->{
                            Log.d("println",it.value.postRequest.description.toString())
                        }
                        is Resource.Failure ->{
                            Log.d("println", "Error in adding item to list")
                        }
                        else ->{}
                    }
                })

            }

            if(binding.cbAddedSalt.isChecked){

                signUpUserAvoid(15, "Added salt")

                mainViewModel.signUpUserAvoidMVM.observe(viewLifecycleOwner, Observer { it ->
                    when(it){
                        is Resource.DoNothing->{}
                        is Resource.Success ->{
                            Log.d("println",it.value.postRequest.description.toString())
                        }
                        is Resource.Failure ->{
                            Log.d("println", "Error in adding item to list")
                        }
                        else ->{}
                    }
                })

            }

            Snackbar.make(view, "Adding items to Avoid List.", 5000)
                .setAnchorView(activity?.findViewById(R.id.bottomNavigationView))
                .show()

            binding.buttonSubmit.visibility = View.INVISIBLE

        }

        binding.buttonNext.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_singupAvoidFragment_to_payAttentionFragment)
        }




        return view

    }

    private fun signUpUserAvoid(ingredientCode: Int, ingredientName: String){
        val jsonToken = getJsonTokenSharedPreferences()

        if (jsonToken != null) {
            mainViewModel.signUpUserAvoid(jsonToken, ingredientCode, ingredientName)
        } else{
            Toast.makeText(requireContext(), "Can't get Token from local Storage Login again.",
                Toast.LENGTH_LONG).show()
        }
    }

    private fun getJsonTokenSharedPreferences():String? {
        //getting Json token from shared preferences.

        val sharedPreference = activity?.getSharedPreferences("jsonTokenFile", Context.MODE_PRIVATE)
        val jsonToken = sharedPreference?.getString("jsonTokenKey","NoToken");

        return jsonToken;
    }
}