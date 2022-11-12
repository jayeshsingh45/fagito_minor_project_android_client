package com.nexlatech.fagito

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nexlatech.fagito.api.FagitoService
import com.nexlatech.fagito.api.Resource
import com.nexlatech.fagito.api.RetrofitHelper
import com.nexlatech.fagito.databinding.FragmentHomeBinding
import com.nexlatech.fagito.repository.FagitoRepository
import com.nexlatech.fagito.viewmodel.MainViewModel
import com.nexlatech.fagito.viewmodel.MainViewModelFactory

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        //mainViewModel setup
        val fagitoService = RetrofitHelper.getInstance().create(FagitoService::class.java)
        val repository = FagitoRepository(fagitoService, requireContext())
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository))[MainViewModel::class.java]

        val jsonToken = getJsonTokenSharedPreferences()
        Log.d("println", "jsonToken Shared preferences " + jsonToken.toString())

        if (jsonToken != null) {
            mainViewModel.foodRecommendation(jsonToken)
        } else{
            Toast.makeText(requireContext(), "Can't get Token from local Storage Login again.",
                Toast.LENGTH_LONG).show()
        }

        //RecyclerView Setup
        val adapter = ProductsAdapter(mainViewModel, this, jsonToken!!)
        val recyclerView = binding.rvProduct
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mainViewModel.foodRecommendationMVM.observe(viewLifecycleOwner, Observer { it ->
            when(it){
                is Resource.DoNothing->{}
                is Resource.Success ->{
                    adapter.setData(it.value.postRequest.content.userProductRecommendationList)

                    binding.pbProgressBar.visibility = View.INVISIBLE
                    makeEveryThingVisible()

                }
                is Resource.Failure ->{
                    binding.tvError.visibility = View.VISIBLE
                    if(it.errorCode == 404){
                        binding.tvError.text = it.errorBody
                    }else{
                        binding.tvError.text = "Error has occurred. Try again later."
                    }

                }
                else ->{}
            }
        })


        return view
    }

    private fun makeEveryThingVisible() {
        binding.rvProduct.visibility = View.VISIBLE
    }

    private fun getJsonTokenSharedPreferences():String? {
        //getting Json token from shared preferences.

        val sharedPreference = activity?.getSharedPreferences("jsonTokenFile",Context.MODE_PRIVATE)
        val jsonToken = sharedPreference?.getString("jsonTokenKey","NoToken");

        return jsonToken;
    }


}