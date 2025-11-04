package com.example.xmlproject.repository

import com.example.xmlproject.data.api.RetrofitClient
import com.example.xmlproject.data.model.request.LoginRequest
import com.example.xmlproject.data.model.request.SignUpRequest
import com.example.xmlproject.data.model.response.DialCodeResponse
import com.example.xmlproject.data.model.response.LoginResponse
import com.example.xmlproject.data.model.response.SignUpResponse
import com.example.xmlproject.data.model.response.TripListResponse
import okhttp3.ResponseBody

import retrofit2.Response

class AuthRepository {

    suspend fun loginUser(
        request: LoginRequest
    ): Response<LoginResponse> {
        return RetrofitClient.api.loginUser(request)
    }

    suspend fun getDialCodes(): Response<DialCodeResponse> {
        return RetrofitClient.api.getDialCodes(
            page = 1,
            take = 10,
            sortColumn = "id",
            sortOrder = "ASC"
        )
    }

    suspend fun signUpUser(
        request: SignUpRequest
    ): Response<SignUpResponse> {
        return RetrofitClient.api.registerUser(request)
    }

    suspend fun getTripList(
        token: String,
        page: Int = 1,
        take: Int = 10,
        sortColumn: String = "id",
        sortOrder: String = "ASC",
        isUpcoming: Boolean = false
    ): Response<TripListResponse> {
        if (token.isEmpty()) {
            return Response.error(401, ResponseBody.create(null, "Token not found"))
        }

        return try {
            RetrofitClient.api.getTripList(
                authorization = "Bearer $token",
                page = page,
                take = take,
                sortColumn = sortColumn,
                sortOrder = sortOrder,
                isUpcoming = isUpcoming
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Response.error(500, ResponseBody.create(null, e.message ?: "Unknown error"))
        }
    }

    suspend fun getTripDetails(token: String, tripId: Int) =
        RetrofitClient.api.getTripDetails("Bearer $token", tripId)


}

