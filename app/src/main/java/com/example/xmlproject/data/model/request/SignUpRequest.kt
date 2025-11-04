package com.example.xmlproject.data.model.request

data class SignUpRequest(
    val countryId: Int,
    val phone: String,
    val email: String,
    val password: String,
    val name: String,
    val role: String
)