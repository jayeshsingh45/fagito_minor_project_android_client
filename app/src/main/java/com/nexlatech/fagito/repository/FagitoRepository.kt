package com.nexlatech.fagito.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexlatech.fagito.api.FagitoService
import com.nexlatech.fagito.api.Resource
import com.nexlatech.fagito.models.*
import com.nexlatech.fagito.utils.NetworkUtils
import java.util.*
import kotlin.concurrent.schedule

class FagitoRepository(
    private val fagitoService: FagitoService,
    private val applicationContext: Context,
    ) {

    //userCanEatOrNot Live data
    private val userCanEatOrNotLiveData = MutableLiveData<Resource<userCanEatOrNot>>()
    val userCanEatOrNotLive: LiveData<Resource<userCanEatOrNot>>
        get() = userCanEatOrNotLiveData

    suspend fun userCanEatOrNot(){
        val userCanEatOrNotRequest = userCanEatOrNotRequest("8901058897159")

        val result = fagitoService.userCanEatOrNot(
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyTmFtZSI6InRlc3QyIiwiaWF0IjoxNjY2MjgwODMwLCJleHAiOjE2OTc4MTY4MzB9.qbBSvO4HmcQgtZgMnMv4RwlProgcPW7wrcFf6R4s9U4",
            userCanEatOrNotRequest
        )

        if(result?.body() != null){
            userCanEatOrNotLiveData.postValue(Resource.Success(result.body()!!))
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
        if(NetworkUtils.isInternetAvailable(applicationContext)){
            loginLiveData.postValue(Resource.Loading)
            try {
                val loginsData = LoginData(userName, password)
                val result = fagitoService.getLoginToken(loginsData)

                if(result.body() != null){
                    Log.d("println", "204:" + result.body().toString())
                    loginLiveData.postValue(Resource.Success(result.body()!!))

                    Timer().schedule(2000){
                        loginLiveData.postValue(Resource.DoNothing)
                    }

                }else if (result.errorBody() != null){
                    if(result.code() == 401){
                        Log.d("println", "22:" + result.code().toString())
                        loginLiveData.postValue(Resource.Failure(
                            false, result.code(), "Email or password is incorrect."))

                        Timer().schedule(2000){
                            loginLiveData.postValue(Resource.DoNothing)
                        }
                    }else{
                        Log.d("println", "24: " + result.errorBody()!!.string().toString())
                        loginLiveData.postValue(Resource.Failure(
                            false, result.code(), result.errorBody()!!.string().toString()))

                        Timer().schedule(2000){
                            loginLiveData.postValue(Resource.DoNothing)
                        }
                    }
                }
            }catch (e: Exception){
                Log.d("println", "29: ${e.message.toString()}")
                loginLiveData.postValue(Resource.Failure(
                    false, 1, e.message.toString()))

                Timer().schedule(2000){
                    loginLiveData.postValue(Resource.DoNothing)
                }
            }

        }else {
            loginLiveData.postValue(Resource.Failure(
                false, 0, "Check Internet."))
            Log.d("println", "27: Check Internet.")

            Timer().schedule(2000){
                loginLiveData.postValue(Resource.DoNothing)
            }
        }
    }
}