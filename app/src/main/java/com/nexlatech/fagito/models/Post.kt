package com.nexlatech.fagito.models

data class Post(
    val content: Content,
    val description: String,
    val operationId: String,
    val responseCode: Int,
    val summary: String
)