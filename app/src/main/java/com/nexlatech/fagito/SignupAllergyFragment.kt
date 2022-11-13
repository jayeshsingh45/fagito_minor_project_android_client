package com.nexlatech.fagito

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.nexlatech.fagito.api.FagitoService
import com.nexlatech.fagito.api.Resource
import com.nexlatech.fagito.api.RetrofitHelper
import com.nexlatech.fagito.databinding.FragmentSignupAllergyBinding
import com.nexlatech.fagito.databinding.FragmentSignupBinding
import com.nexlatech.fagito.repository.FagitoRepository
import com.nexlatech.fagito.viewmodel.MainViewModel
import com.nexlatech.fagito.viewmodel.MainViewModelFactory

class SignupAllergyFragment : Fragment() {
    private var _binding: FragmentSignupAllergyBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignupAllergyBinding.inflate(inflater, container, false)
        val view = binding.root

        //mainViewModel setup
        val fagitoService = RetrofitHelper.getInstance().create(FagitoService::class.java)
        val repository = FagitoRepository(fagitoService, requireContext())
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository))[MainViewModel::class.java]


        binding.acbSubmit.setOnClickListener {

            if(binding.cbPeanuts.isChecked){

                signUpAllergy(5,"Peanuts")

                mainViewModel.signUpAllergyMVM.observe(viewLifecycleOwner, Observer { it ->
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

            if(binding.cbSoya.isChecked){
                signUpAllergy(2,"Soy")

                mainViewModel.signUpAllergyMVM.observe(viewLifecycleOwner, Observer { it ->
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

            if(binding.cbWheat.isChecked){
                signUpAllergy(3,"Wheat")

                mainViewModel.signUpAllergyMVM.observe(viewLifecycleOwner, Observer { it ->
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

            if(binding.cbNuts.isChecked){
                signUpAllergy(4,"Nuts")

                mainViewModel.signUpAllergyMVM.observe(viewLifecycleOwner, Observer { it ->
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

            if(binding.cbMilk.isChecked){
                signUpAllergy(1,"Milk")

                mainViewModel.signUpAllergyMVM.observe(viewLifecycleOwner, Observer { it ->
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



            if(binding.cbSulphite.isChecked){
                signUpAllergy(6,"Sulphite")

                mainViewModel.signUpAllergyMVM.observe(viewLifecycleOwner, Observer { it ->
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

            Snackbar.make(view, "Allergen's added to allergy list.", 5000)
                .setAnchorView(activity?.findViewById(R.id.bottomNavigationView))
                .show()

            binding.acbSubmit.visibility = View.INVISIBLE

        }

        binding.acbNext.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_signupAllergyFragment_to_singupAvoidFragment)

        }


        return view
    }

    private fun signUpAllergy(allergyCode: Int, allergyName: String){
//        val jsonToken = getJsonTokenSharedPreferences()
        val jsonToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyTmFtZSI6InRlc3QxMCIsImlhdCI6MTY2ODI5Mjc4NiwiZXhwIjoxNjk5ODI4Nzg2fQ.o3H3GUxK4dPit9VaD8ANPD8BY66jCoF-ZpH5vvYWbak"

        if (jsonToken != null) {
            mainViewModel.signUpAllergy(jsonToken, allergyCode, allergyName)
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