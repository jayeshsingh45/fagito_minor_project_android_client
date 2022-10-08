package com.nexlatech.fagito.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexlatech.fagito.models.getProfileDetails
import com.nexlatech.fagito.repository.FagitoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: FagitoRepository):ViewModel() {
    init {
        viewModelScope.launch( Dispatchers.IO ){
            repository.getProfileDetails("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyTmFtZSI6InRlc3QyIiwiaWF0IjoxNjY1MTY5ODcxLCJleHAiOjE2OTY3MDU4NzF9.lSO77DUpXD0NN1uKYCNDd4rUiOAf0WNP018Le6jshPk")

        }
    }

    val profileDetailsView : LiveData<getProfileDetails>
    get() = repository.profileDetails

}