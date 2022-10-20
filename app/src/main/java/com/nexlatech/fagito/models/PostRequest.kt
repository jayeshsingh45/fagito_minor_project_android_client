package com.nexlatech.fagito.models

data class PostRequest(
    val content: ContentX,
    val description: String,
    val operationId: String,
    val responseCode: Int,
    val summary: String
)