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
import com.nexlatech.fagito.api.FagitoService
import com.nexlatech.fagito.api.Resource
import com.nexlatech.fagito.api.RetrofitHelper
import com.nexlatech.fagito.databinding.FragmentLoginBinding
import com.nexlatech.fagito.repository.FagitoRepository
import com.nexlatech.fagito.viewmodel.MainViewModel
import com.nexlatech.fagito.viewmodel.MainViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(layoutInflater)

        val view = binding.root

        val fagitoService = RetrofitHelper.getInstance().create(FagitoService::class.java)
        val repository = FagitoRepository(fagitoService, requireContext())
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository))[MainViewModel::class.java]


        mainViewModel.getLoginToken.observe(viewLifecycleOwner, Observer { it ->
            when(it){
                is Resource.DoNothing -> {
                }
                is Resource.Success -> {
                    Log.d("println",it.value.postRequest.content.accessToken)

                    saveToSharedPreferences(
                        "jsonTokenKey", it.value.postRequest.content.accessToken)


                    activity?.let{
                        val intent = Intent(it, HomeActivity::class.java)
                        it.startActivity(intent)
                    }

                }
                is Resource.Failure -> {
                    Log.d("println",it.errorCode.toString())
                }
                else -> {}
            }
        })

        binding.acbLogin.setOnClickListener {
            mainViewModel.login(binding.tiEmail.text.toString(), binding.tiPassword.text.toString())
//            Log.d("println",binding.tiEmail.text.toString())
        }

        return view


    }

    private fun saveToSharedPreferences(key: String, token: String){
        //saving jsonToken to shared Preferences

        val sharedPref = activity?.getSharedPreferences("jsonTokenFile",Context.MODE_PRIVATE) ?: return
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