package com.nexlatech.fagito.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexlatech.fagito.api.FagitoService
import com.nexlatech.fagito.models.getProfileDetails

class FagitoRepository(private val fagitoService: FagitoService) {

    private val profileDetailsLiveData = MutableLiveData<getProfileDetails>()

    val profileDetails: LiveData<getProfileDetails>
    get() = profileDetailsLiveData

    suspend fun getProfileDetails(token: String){
        val result = fagitoService.getProfileDetails(token)
        if(result?.body() != null){
            profileDetailsLiveData.postValue(result.body())
        }
    }
}