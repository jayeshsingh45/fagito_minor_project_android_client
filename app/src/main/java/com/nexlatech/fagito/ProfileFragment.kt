package com.nexlatech.fagito

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.nexlatech.fagito.api.FagitoService
import com.nexlatech.fagito.api.RetrofitHelper
import com.nexlatech.fagito.databinding.FragmentProfileBinding
import com.nexlatech.fagito.repository.FagitoRepository
import com.nexlatech.fagito.viewmodel.MainViewModel
import com.nexlatech.fagito.viewmodel.MainViewModelFactory

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        //mainViewModel setup
        val fagitoService = RetrofitHelper.getInstance().create(FagitoService::class.java)
        val repository = FagitoRepository(fagitoService, requireContext())
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository))[MainViewModel::class.java]

        binding.acbLogout.setOnClickListener {
            mainViewModel.deleteTokenFromSharedPreferences()
            Toast.makeText(requireContext(),"Logout success",Toast.LENGTH_SHORT).show()

            activity?.let{
                val intent = Intent(it, AuthActivity::class.java)
                it.startActivity(intent)
            }
        }

        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


