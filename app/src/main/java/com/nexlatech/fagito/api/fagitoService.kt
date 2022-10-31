package com.nexlatech.fagito.api

import com.nexlatech.fagito.models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface FagitoService {
    @POST("/fagito_minor_project_1.0/profile/getProfileDetails")
    suspend fun getProfileDetails(
        @Header("access-token") token: String
    ): Response<getProfileDetails>

    @POST("/fagito_minor_project_1.0/authentication/login")
    suspend fun getLoginToken(
        @Body loginData: LoginData
    ): Response<getLoginToken>

    @POST("/fagito_minor_project_1.0/foodData/getUserCanEatOrNot")
    suspend fun userCanEatOrNot(
        @Header("access-token") token: String,
        @Body userCanEatOrNotRequest: userCanEatOrNotRequest
    ): Response<userCanEatOrNot>

}