package com.nexlatech.fagito.models

data class ContentXX(
    val productImageLink: String,
    val productName: String,
    val suggestIcon: Int,
    val suggestText: String,
    val userAllergenMatchList: List<String>,
    val userAvoidMatchIngredientList: List<String>,
    val product_amazon_link: String,
    val product_flipkart_link: String
)