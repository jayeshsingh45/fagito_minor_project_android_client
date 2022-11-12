package com.nexlatech.fagito

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nexlatech.fagito.databinding.CustomRowBinding
import com.nexlatech.fagito.models.UserProductRecommendation
import com.nexlatech.fagito.viewmodel.MainViewModel

class ProductsAdapter(private val mProductRecommendation: MainViewModel,private val fragment: HomeFragment)
    :RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    private var productList = emptyList<UserProductRecommendation>()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductsAdapter.ProductsViewHolder {
        return ProductsViewHolder(
            CustomRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }
    class ProductsViewHolder(val binding: CustomRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ProductsAdapter.ProductsViewHolder, position: Int) {
        val currentItem = productList[position]

        holder.binding.clRootView.setOnClickListener {
            Log.d("println","opening bottom modal from recycler view.")
        }

        holder.binding.apply {
            val product = productList[position]
            tvProductNameCR.text = product.product_name

            //circular progress loading.
            val circularProgressDrawable = fragment.context?.let { CircularProgressDrawable(it) }

            if (circularProgressDrawable != null) {
                circularProgressDrawable.strokeWidth = 5f
                circularProgressDrawable.start()
                circularProgressDrawable.centerRadius = 30f
            }
            //loading image
            Glide.with(fragment)
                .load(product.product_image_link)
                .fitCenter()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(circularProgressDrawable)
                .into(ivProductImageCR)
        }

    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun setData(product : List<UserProductRecommendation>){
        this.productList = product
        notifyDataSetChanged()
    }


}