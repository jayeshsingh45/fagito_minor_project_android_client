package com.nexlatech.fagito.models

data class PostRequestX(
    val content: ContentXX,
    val description: String,
    val operationId: String,
    val responseCode: Int,
    val title: String
)