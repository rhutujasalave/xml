package com.example.xmlproject.data.model.response


data class DialCode(
    val id: Int,
    val diaCode: String,
    val countryCode: String
)

data class DialCodeResponse(
    val data: List<DialCode>,
    val pagination: Pagination
)

data class Pagination(
    val currentPage: Int,
    val pageSize: Int,
    val totalItems: Int,
    val totalPages: Int
)
