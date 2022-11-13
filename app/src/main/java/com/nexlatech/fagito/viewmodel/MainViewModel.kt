package com.nexlatech.fagito.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexlatech.fagito.api.Resource
import com.nexlatech.fagito.models.*
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

    fun deleteTokenFromSharedPreferences(){
        repository.deleteTokenFromSharedPreferences()
    }


    fun login(userName: String, password: String){
        viewModelScope.launch {
            repository.login(userName, password)
        }
    }

    fun userCanEatOrNot(UPCCode: String, token: String){
        viewModelScope.launch {
            repository.userCanEatOrNot(UPCCode, token)
        }
    }
    val userCanEatOrNotLiveMVM: LiveData<Resource<userCanEatOrNot>>
        get() = repository.userCanEatOrNotLive

    fun foodRecommendation(token: String){
        viewModelScope.launch {
            repository.foodRecommendation(token)
        }
    }
    val foodRecommendationMVM: LiveData<Resource<FoodRecommendationModel>>
        get() = repository.foodRecommendationLive


    fun signUpAllergy(token: String, allergyCode: Int, allergyName:String){
        viewModelScope.launch {
            repository.signUpAllergy(token, allergyCode, allergyName)
        }
    }
    val signUpAllergyMVM: LiveData<Resource<SignUpAllergyResponse>>
        get() = repository.signupAllergyLive

    val getLoginToken : LiveData<Resource<getLoginToken>>
        get() = repository.logins

}