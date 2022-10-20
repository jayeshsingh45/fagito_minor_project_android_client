package com.nexlatech.fagito.api

import com.nexlatech.fagito.models.LoginData
import com.nexlatech.fagito.models.getLoginToken
import com.nexlatech.fagito.models.getProfileDetails
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface FagitoService {
    @POST("/fagito_minor_project_1.0/profile/getProfileDetails")
    suspend fun getProfileDetails(@Header("access-token") token: String): Response<getProfileDetails>

    @POST("/fagito_minor_project_1.0/authentication/login")
    suspend fun getLoginToken(
        @Body loginData: LoginData
    ): Response<getLoginToken>

}