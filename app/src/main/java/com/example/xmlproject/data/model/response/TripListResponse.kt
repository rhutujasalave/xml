package com.example.xmlproject.data.model.response

data class TripListResponse(
    val data: List<TripItem>?
)

data class TripItem(
    val id: Int?,
    val destinationImage: String?,
    val status: String?,
    val category: Category?,
    val receiver: Receiver?,
    val dropoffAddress: DropoffAddress?,
    val customer: Customer?,
    val createdAt: String?,
    val totalCost: Double?,
    val isUpcoming: Boolean? = null

)

data class Receiver(
    val name: String?,
    val phone: String?,
    val diaCode: String?
)

//data class DropoffAddress(
//    val line1: String?,
//    val line2 :String?,
//    val city: String?,
//    val state: String?,
//    val postal_code: String?
//)

data class DropoffAddress(
    val line1: String?,
    val line2: String?,
    val city: String?,
    val state: String?,
    val postal_code: String?
) {
    fun getFormattedAddress(): String {
        return listOfNotNull(line1, line2, city, state, postal_code)
            .joinToString(", ")
    }
}


data class Category(
    val name: String?,
    val price: Int?
)

data class Customer(
    val name: String?,
    val profileImage: String?
)
