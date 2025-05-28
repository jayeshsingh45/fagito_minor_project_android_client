package com.nexlatech.fagito.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
//    private const val BASE_URL = "https://nexla-271516.uc.r.appspot.com/"
    private const val BASE_URL = "http://192.168.56.1:80/"

    fun getInstance(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}