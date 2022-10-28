package com.nexlatech.fagito

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nexlatech.fagito.databinding.FragmentBottomModalBinding

class BottomModalFragment : BottomSheetDialogFragment() {
    companion object {
        const val TAG = "ModalBottomSheet"
    }
    private var _binding: FragmentBottomModalBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBottomModalBinding.inflate(inflater, container, false)
        val view = binding.root

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

        val url = "https://jayeshmax.github.io/milky_bar.jpg"

        //loading image
        Glide.with(this)
            .load(url)
            .fitCenter()
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .placeholder(circularProgressDrawable)
            .into(binding.ivProductImage)


        return view
    }
}