package com.example.xmlproject.data.model.response

data class SignUpResponse(
    val statusCode: Int,
    val message: String,
    val errors: List<String>?
)