package com.nexlatech.fagito.models

data class UserProductRecommendation(
    val id: Int,
    val product_amazon_link: String,
    val product_flipkart_link: String,
    val product_image_link: String,
    val product_name: String,
    val upc_code: String
)