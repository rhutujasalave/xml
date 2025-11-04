package com.example.xmlproject.data.model.response


//data class TripDetailResponse(
//    val id: Int?,
//    val distanceKm: Double?,
//    val totalMaterialCost: Double?,
//    val materialCost: Double?,
//    val transportCost: Double?,
//    val totalCost: Double?,
//    val status: String?,
//    val createdAt: String?,
//    val destinationImage: String?,
//    val receiver: Receiver?,
//    val dropoffAddress: DropoffAddress?,
//    val category: Category?,
//    val customer: Customer?
//) : Serializable

//data class Receiver(
//    val name: String?,
//    val phone: String?,
//    val diaCode: String?
//) : Serializable

//data class DropoffAddress(
//    val line1: String?,
//    val line2: String?,
//    val city: String?,
//    val state: String?
//) : Serializable
//
//data class Category(
//    val name: String?,
//    val itemType: String?,
//    val price: Double?
//) : Serializable
//
//data class Customer(
//    val name: String?,
//    val profileImage: String?
//) : Serializable


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
