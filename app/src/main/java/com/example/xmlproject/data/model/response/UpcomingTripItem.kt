package com.example.xmlproject.data.model.response


data class UpcomingTripItem(
    val customerName: String?,
    val pickupLocation: String?,
    val dropLocation: String?,
    val materialType: String?,
    val factory: String?,
    val quantity: String?,
    val cost: String?,
    val dateTime: String?,
    val status: String?,
    val customerImage: String?
)
