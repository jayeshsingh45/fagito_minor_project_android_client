package com.nexlatech.fagito

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.Toast
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
import com.nexlatech.fagito.models.userCanEatOrNot
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
        val UPCCode = bundle!!.getString("UPCCode")
        val token = bundle.getString("token")
        Log.d("println",UPCCode.toString())

        if (token != null) {
            mainViewModel.userCanEatOrNot(UPCCode.toString(),token)
        } else{
            Toast.makeText(requireContext(), "Can't get Token from local Storage Login again.",
                Toast.LENGTH_LONG).show()
        }

        binding.ivClose.setOnClickListener {
            dismiss()
        }

        binding.tvErrorAndLoading.text = "Loading..."
        binding.tvErrorAndLoading.visibility = VISIBLE
        mainViewModel.userCanEatOrNotLiveMVM.observe(viewLifecycleOwner, Observer { it ->
            when(it){
                is Resource.DoNothing->{}
                is Resource.Success ->{
                    Log.d("println",it.value.postRequest.content.productImageLink)

                    settingViewsValue(it.value)

                    makeEveryThingVisible()
                    binding.tvErrorAndLoading.visibility = INVISIBLE
                }
                is Resource.Failure ->{
                    if(it.errorCode == 404){
                        binding.tvErrorAndLoading.text = it.errorBody
                    }

                }
                else ->{}
            }
        })

        return view
    }

    private fun makeEveryThingVisible(){
        binding.ivProductImage.visibility = VISIBLE
        binding.tvProductName.visibility = VISIBLE
        binding.ivIcon.visibility = VISIBLE
        binding.tvEatOrNot.visibility = VISIBLE
        binding.ivBuyAmazon.visibility = VISIBLE
        binding.ivBuyFlipkart.visibility = VISIBLE

    }

    private fun settingViewsValue(values: userCanEatOrNot){
        val url = values.postRequest.content.productImageLink
        binding.tvProductName.text = values.postRequest.content.productName
        binding.tvEatOrNot.text = values.postRequest.content.suggestText
        binding.ivBuyFlipkart.setOnClickListener {
            val urlFlipkart: String = values.postRequest.content.product_flipkart_link
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlFlipkart))
            startActivity(intent)
        }
        binding.ivBuyAmazon.setOnClickListener {
            val urlAmazon: String = values.postRequest.content.product_amazon_link
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlAmazon))
            startActivity(intent)
        }

        //circular progress loading.
        val circularProgressDrawable = context?.let { CircularProgressDrawable(it) }
        if (circularProgressDrawable != null) {
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.start()
            circularProgressDrawable.centerRadius = 30f
        }
        //loading image
        Glide.with(this)
            .load(url)
            .fitCenter()
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .placeholder(circularProgressDrawable)
            .into(binding.ivProductImage)

        when (values.postRequest.content.suggestIcon){
            1 -> binding.ivIcon.setImageResource(R.drawable.red_cross)
            2 -> binding.ivIcon.setImageResource(R.drawable.yellow_stop)
            3 -> binding.ivIcon.setImageResource(R.drawable.green_tick)
        }
    }

}
