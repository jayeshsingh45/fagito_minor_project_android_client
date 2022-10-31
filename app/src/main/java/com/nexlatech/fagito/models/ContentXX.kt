package com.nexlatech.fagito.models

data class ContentXX(
    val productImageLink: String,
    val productName: String,
    val suggestIcon: Int,
    val suggestText: String,
    val userAllergenMatchList: List<String>,
    val userAvoidMatchIngredientList: List<String>
)