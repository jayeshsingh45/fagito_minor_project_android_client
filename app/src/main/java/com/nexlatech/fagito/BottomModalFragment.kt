package com.nexlatech.fagito

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nexlatech.fagito.api.FagitoService
import com.nexlatech.fagito.api.Resource
import com.nexlatech.fagito.api.RetrofitHelper
import com.nexlatech.fagito.databinding.FragmentBottomModalBinding
import com.nexlatech.fagito.repository.FagitoRepository
import com.nexlatech.fagito.viewmodel.MainViewModel
import com.nexlatech.fagito.viewmodel.MainViewModelFactory

class BottomModalFragment : BottomSheetDialogFragment() {
    companion object {
        const val TAG = "ModalBottomSheet"
    }
    private var _binding: FragmentBottomModalBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBottomModalBinding.inflate(inflater, container, false)
        val view = binding.root

        //mainViewModel setup
        val fagitoService = RetrofitHelper.getInstance().create(FagitoService::class.java)
        val repository = FagitoRepository(fagitoService, requireContext())
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository))[MainViewModel::class.java]

        val bundle = arguments
        val message = bundle!!.getString("mText")
        Log.d("println",message.toString())

        binding.ivClose.setOnClickListener {
            dismiss()
        }

        //circular progress loading.
        val circularProgressDrawable = context?.let { CircularProgressDrawable(it) }
        if (circularProgressDrawable != null) {
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.start()
            circularProgressDrawable.centerRadius = 30f
        }




        mainViewModel.userCanEatOrNotLiveMVM.observe(viewLifecycleOwner, Observer { it ->
            when(it){
                is Resource.DoNothing->{}
                is Resource.Success ->{
                    Log.d("println",it.value.postRequest.content.productName)

                    val url = it.value.postRequest.content.productImageLink

                    //loading image
                    Glide.with(this)
                        .load(url)
                        .fitCenter()
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .placeholder(circularProgressDrawable)
                        .into(binding.ivProductImage)
                }
                is Resource.Failure ->{
                    Log.d("println","Bottom Modal sheet api request fail.")
                }
                else ->{}
            }
        })

        mainViewModel.userCanEatOrNot()




        return view
    }
}