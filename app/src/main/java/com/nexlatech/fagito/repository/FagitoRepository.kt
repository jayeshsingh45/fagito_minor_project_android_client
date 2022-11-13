package com.nexlatech.fagito.repository

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexlatech.fagito.api.FagitoService
import com.nexlatech.fagito.api.Resource
import com.nexlatech.fagito.models.*
import com.nexlatech.fagito.utils.NetworkUtils
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.onSuccess
import com.skydoves.sandwich.request
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.util.*
import java.util.concurrent.Flow
import kotlin.concurrent.schedule

class FagitoRepository(
    private val fagitoService: FagitoService,
    private val applicationContext: Context,
    ) {

    //signupAllergyLive Live data
    private val signUpAllergyLiveData = MutableLiveData<Resource<SignUpAllergyResponse>>()
    val signupAllergyLive: LiveData<Resource<SignUpAllergyResponse>>
        get() = signUpAllergyLiveData

    suspend fun signUpAllergy(token:String,allergyCode:Int, allergyName:String ){
        val signupAllergySend = SignupAllergySend(allergyCode, allergyName)

        val response = fagitoService.singUpAllergy(token,signupAllergySend)

        if(response.isSuccessful && response.body() != null){
            signUpAllergyLiveData.postValue(Resource.Success(response.body()!!))
        }else if (response.errorBody() != null){
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            val descripitonText = errorObj.getJSONObject("postRequest").getString("description")
            signUpAllergyLiveData.postValue(Resource.Failure(false,response.code(),descripitonText))

            Log.d("println",errorObj.getJSONObject("postRequest").getString("description"))
        }else{
            Log.d("println", "Unknown error occurred.")
        }
    }


    //foodRecommendation Live data
    private val foodRecommendationLiveData = MutableLiveData<Resource<FoodRecommendationModel>>()
    val foodRecommendationLive: LiveData<Resource<FoodRecommendationModel>>
        get() = foodRecommendationLiveData

    suspend fun foodRecommendation(token: String){

        val response = fagitoService.getFoodRecommendation(token)

        if(response.isSuccessful && response.body() != null){
            foodRecommendationLiveData.postValue(Resource.Success(response.body()!!))
        }else if (response.errorBody() != null){
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            val descripitonText = errorObj.getJSONObject("postRequest").getString("description")
            userCanEatOrNotLiveData.postValue(Resource.Failure(false,response.code(),descripitonText))

            Log.d("println",errorObj.getJSONObject("postRequest").getString("description"))
        }else{
            Log.d("println", "Unknown error occurred.")
        }

    }

    //userCanEatOrNot Live data
    private val userCanEatOrNotLiveData = MutableLiveData<Resource<userCanEatOrNot>>()
    val userCanEatOrNotLive: LiveData<Resource<userCanEatOrNot>>
        get() = userCanEatOrNotLiveData

    suspend fun userCanEatOrNot(UPCCode:String, token:String){
        val userCanEatOrNotRequest = userCanEatOrNotRequest(UPCCode);

        val response = fagitoService.userCanEatOrNot(
            token,
            userCanEatOrNotRequest
        )

        if(response.isSuccessful && response.body() != null){
            userCanEatOrNotLiveData.postValue(Resource.Success(response.body()!!))
        }else if (response.errorBody() != null){
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            val descripitonText = errorObj.getJSONObject("postRequest").getString("description")
            userCanEatOrNotLiveData.postValue(Resource.Failure(false,response.code(),descripitonText))

            Log.d("println",errorObj.getJSONObject("postRequest").getString("description"))
        }else{
            Log.d("println", "Unknown error occurred.")
        }

    }


    //profileDetails live data
    private val profileDetailsLiveData = MutableLiveData<getProfileDetails>()
    val profileDetails: LiveData<getProfileDetails>
        get() = profileDetailsLiveData

    suspend fun getProfileDetails(token: String){
        val result = fagitoService.getProfileDetails(token)
        if(result?.body() != null){
            profileDetailsLiveData.postValue(result.body())
        }
    }

    //Login Live data
    private val loginLiveData = MutableLiveData<Resource<getLoginToken>>()
    val logins: LiveData<Resource<getLoginToken>>
        get() = loginLiveData

    suspend fun login(userName: String, password: String) {
        val loginDataSend = LoginData(password,userName);

        val response = fagitoService.getLoginToken(
            loginDataSend
        )

        if(response.isSuccessful && response.body() != null){
            loginLiveData.postValue(Resource.Success(response.body()!!))
        }else if (response.errorBody() != null){
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            val descripitonText = errorObj.getJSONObject("postRequest").getString("description")
            userCanEatOrNotLiveData.postValue(Resource.Failure(false,response.code(),descripitonText))

            Log.d("println",errorObj.getJSONObject("postRequest").getString("description"))
        }else{
            Log.d("println", "Unknown error occurred.")
        }
    }

    fun deleteTokenFromSharedPreferences(){
        //getting Json token from shared preferences.

        val sharedPreference = applicationContext.getSharedPreferences("jsonTokenFile", Context.MODE_PRIVATE)
        val jsonToken = sharedPreference?.getString("jsonTokenKey","defaultName");

        sharedPreference.edit {
            this.clear()
        }
    }
}