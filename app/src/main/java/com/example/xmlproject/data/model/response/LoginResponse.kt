package com.example.xmlproject.data.model.response

//data class LoginResponse(
//    val statusCode: Int?,
//    val message: String?,
//    val token: String? = null,
//    val user: User? = null
//)
//
//data class User(             //to hold user-related data returned by the API after a successful login.
//    val id: String?,         //in future if i want to store data by sharepreferance it is easier
//    val name: String?,
//    val phone: String?
//)



data class LoginResponse(
    val statusCode: Int?,
    val message: String?,
    val data: LoginData?
)

data class LoginData(
    val accessToken: String?,
    val refreshToken: String?,
    val id: Int?,
    val name: String?,
    val role: String?,
    val email: String?,
    val phone: String?,
    val diaCode: String?,
    val status: String?,
    val isOnline: Boolean?,
    val profileImage: ProfileImage?
)

data class ProfileImage(
    val key: String?,
    val url: String?
)
