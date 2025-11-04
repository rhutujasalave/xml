//package com.example.xmlproject.adapter
////
////import android.view.LayoutInflater
////import android.view.ViewGroup
////import androidx.recyclerview.widget.RecyclerView
////import com.example.xmlproject.R
////import com.example.xmlproject.data.model.response.TripItem
////import com.example.xmlproject.databinding.ItemUpcomingTripBinding
////
////class UpcomingTripAdapter(
////    private val trips: List<TripItem>,
////    private val onTripClick: (TripItem) -> Unit
////) : RecyclerView.Adapter<UpcomingTripAdapter.UpcomingTripViewHolder>() {
////
////    inner class UpcomingTripViewHolder(val binding: ItemUpcomingTripBinding) :
////        RecyclerView.ViewHolder(binding.root) {
////        fun bind(trip: TripItem) = with(binding) {
////            tvCustomerName.text = trip.customer?.name ?: "N/A"
////            tvPickup.text = trip.pickupAddress?.line1 ?: "N/A"
////            tvDrop.text = trip.dropoffAddress?.line1 ?: "N/A"
////            tvMaterialType.text = "Type : ${trip.category?.name ?: "N/A"}"
////            tvFactory.text = "Factory : ${trip.dropoffAddress?.city ?: "N/A"}"
////            tvQuantity.text = "Quantity : ${trip.options?.firstOrNull()?.value ?: "N/A"}"
////            tvCost.text = trip.totalAmount?.let { "₹$it" } ?: "₹0"
////
////            Glide.with(imgProfile.context)
////                .load(trip.customer?.profileImage)
////                .placeholder(R.drawable.ic_profile)
////                .into(imgProfile)
////
////            itemView.setOnClickListener { onTripClick(trip) }
////        }
////    }
////
////    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingTripViewHolder {
////        val inflater = LayoutInflater.from(parent.context)
////        val binding = ItemUpcomingTripBinding.inflate(inflater, parent, false)
////        return UpcomingTripViewHolder(binding)
////    }
////
////    override fun onBindViewHolder(holder: UpcomingTripViewHolder, position: Int) {
////        holder.bind(trips[position])
////    }
////
////    override fun getItemCount() = trips.size
////}
//
//
//
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.core.content.ContextCompat
//import androidx.recyclerview.widget.RecyclerView
//import com.example.xmlproject.R
//import com.example.xmlproject.data.model.response.TripItem
//
//class UpcomingTripAdapter(
//    private var tripList: List<TripItem>,
//    private val onItemClick: (TripItem) -> Unit
//) : RecyclerView.Adapter<UpcomingTripAdapter.TripViewHolder>() {
//
//
//    fun upComingList(newList: List<TripItem>) {
//        tripList = newList.filter { trip -> trip.isUpcoming == true || trip.isUpcoming == null }
//        notifyDataSetChanged()
//
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_trip, parent, false)
//        return TripViewHolder(view)
//    }
//
//    override fun getItemCount(): Int = tripList.size
//
//
//    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
//        holder.bind(tripList[position])
//    }
//
//    inner class TripViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val tvPickup = itemView.findViewById<TextView>(R.id.tvPickupLocation)
//        private val tvDropoff = itemView.findViewById<TextView>(R.id.tvDropoffLocation)
//        private val tvDate = itemView.findViewById<TextView>(R.id.tvDate)
//        private val tvStatus = itemView.findViewById<TextView>(R.id.tvStatus)
//        private val tvTotalCost = itemView.findViewById<TextView>(R.id.tvTotalCost)
//
//        fun bind(trip: TripItem) {
//            tvPickup.text = "Factory"
////            tvDropoff.text = trip.dropoffAddress?.line1 ?: "N/A"
//            val dropoff = trip.dropoffAddress
//            tvDropoff.text = listOfNotNull(dropoff?.line1, dropoff?.line2)
//                .joinToString(", ")
//                .ifEmpty { "N/A" }
//            tvDate.text = trip.createdAt ?: "N/A"
////            tvCategory.text = "Category: ${trip.category?.name ?: "General"}"
//            tvTotalCost.text = (trip.totalCost ?: "N/A").toString()
//            val context = itemView.context
//            when (trip.status) {
//                "Completed" -> tvStatus.setTextColor(
//                    ContextCompat.getColor(
//                        context,
//                        R.color.lime_green
//                    )
//                )
//
//                "Cancelled" -> tvStatus.setTextColor(ContextCompat.getColor(context, R.color.gray))
//                "Pending" -> tvStatus.setTextColor(ContextCompat.getColor(context, R.color.blue))
//                else -> tvStatus.setTextColor(ContextCompat.getColor(context, R.color.black))
//            }
//            tvStatus.text = trip.status ?: "Unknown"
//
//            itemView.setOnClickListener {
//                onItemClick(trip)
//            }
//        }
//
//    }
//}
