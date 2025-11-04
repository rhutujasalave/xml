package com.example.xmlproject.data.model.response

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
