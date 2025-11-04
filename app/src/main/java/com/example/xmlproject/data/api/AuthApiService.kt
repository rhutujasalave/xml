package com.example.xmlproject.data.api

import com.example.xmlproject.data.model.request.LoginRequest
import com.example.xmlproject.data.model.request.SignUpRequest
import com.example.xmlproject.data.model.response.DialCodeResponse
import com.example.xmlproject.data.model.response.LoginResponse
import com.example.xmlproject.data.model.response.SignUpResponse
import com.example.xmlproject.data.model.response.TripDetailsResponse
import com.example.xmlproject.data.model.response.TripListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApiService {
    @POST("auth/login")
    suspend fun loginUser(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @GET("auth/dial-codes")
    suspend fun getDialCodes(
        @Query("page") page: Int ,
        @Query("take") take: Int,
        @Query("sortColumn") sortColumn: String,
        @Query("sortOrder") sortOrder: String
    ): Response<DialCodeResponse>

    @POST("auth/register")
    suspend fun registerUser(
        @Body request: SignUpRequest
    ): Response<SignUpResponse>


    @GET("captain/trip/list")
    suspend fun getTripList(
        @Header("Authorization") authorization: String,
        @Query("page") page: Int,
        @Query("take") take: Int,
        @Query("sortColumn") sortColumn: String,
        @Query("sortOrder") sortOrder: String,
        @Query("isUpcoming") isUpcoming: Boolean
    ): Response<TripListResponse>

    @GET("captain/trip")
    suspend fun getTripDetails(
        @Header("Authorization") token: String,
        @Query("tripId") tripId: Int,
        @Header("Accept-Language") language: String = "en"
    ): Response<TripDetailsResponse>

}



