package com.example.xmlproject.data.model.response

data class TripDetailsResponse(
    val id: Int?,
    val customerId: Int?,
    val captainId: Int?,
    val driverId: Int?,
    val paymentId: Int?,
    val distanceKm: Double?,
    val totalMaterialCost: Double?,
    val materialCost: Double?,
    val transportCost: Double?,
    val totalCost: Double?,
    val totalVat: Double?,
    val vatRate: Double?,
    val quantity: Int?,
    val totalLabourCost: Double?,
    val additionalCost: Double?,
    val isUpcoming: Boolean?,
    val status: String?,
    val destinationImage: String?,
    val createdAt: String?,
    val receiver: Receiver?,
    val dropoffAddress: DropoffAddress?,
    val customer: Customer?
)

//data class Receiver(
//    val name: String?,
//    val phone: String?,
//    val countryId: Int?,
//    val diaCode: String?
//)
//
//data class DropoffAddress(
//    val line1: String?,
//    val line2: String?,
//    val city: String?,
//    val state: String?,
//    val postal_code: String?
//)
//
//data class Customer(
//    val id: Int?,
//    val name: String?,
//    val profileImage: String?,
//    val role: String?
//)
