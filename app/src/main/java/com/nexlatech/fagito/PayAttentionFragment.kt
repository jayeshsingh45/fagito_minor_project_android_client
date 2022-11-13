package com.nexlatech.fagito

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nexlatech.fagito.databinding.FragmentPayAttentionBinding


class PayAttentionFragment : Fragment() {
    private var _binding: FragmentPayAttentionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPayAttentionBinding.inflate(layoutInflater)
        val view = binding.root

        binding.acbGotIt.setOnClickListener {
            activity?.let{
                val intent = Intent(it, HomeActivity::class.java)
                it.startActivity(intent)
            }
        }

        return view
    }
}