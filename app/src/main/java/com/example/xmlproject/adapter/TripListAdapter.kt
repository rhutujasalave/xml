//package com.example.xmlproject.adapter
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import androidx.core.content.ContextCompat
//import androidx.recyclerview.widget.RecyclerView
//import com.example.xmlproject.R
//import com.example.xmlproject.data.model.response.TripItem
//import com.example.xmlproject.ui.UpcomingTripDetailsFragment
//import com.example.xmlproject.utils.DateUtils.formatDateTime
//
//class TripListAdapter(
//    private var tripList: List<TripItem>,
//    private val onItemClick: (TripItem) -> Unit
//) : RecyclerView.Adapter<TripListAdapter.TripViewHolder>() {
//
//    fun updateList(newList: List<TripItem>) {
//        tripList = newList.filter { it.isUpcoming == false || it.isUpcoming == null }
//        notifyDataSetChanged()
//    }
//
//    fun updateUpcomingList(newList: List<TripItem>) {
//        tripList = newList.filter { it.isUpcoming == true || it.isUpcoming == null }
//        notifyDataSetChanged()
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
//            val dropoff = trip.dropoffAddress
//            tvDropoff.text = listOfNotNull(dropoff?.line1, dropoff?.line2)
//                .joinToString(", ")
//                .ifEmpty { "N/A" }
//
//            tvDate.text = formatDateTime(trip.createdAt)
//            tvTotalCost.text = (trip.totalCost ?: "N/A").toString()
//
//            val context = itemView.context
//            when (trip.status) {
//                "Completed" -> tvStatus.setTextColor(ContextCompat.getColor(context, R.color.lime_green))
//                "Cancelled" -> tvStatus.setTextColor(ContextCompat.getColor(context, R.color.gray))
//                "Pending" -> tvStatus.setTextColor(ContextCompat.getColor(context, R.color.blue))
//                else -> tvStatus.setTextColor(ContextCompat.getColor(context, R.color.black))
//            }
//            tvStatus.text = trip.status ?: "Unknown"
//
//            itemView.setOnClickListener {
//                val activity = itemView.context as? AppCompatActivity
//                val fragment = UpcomingTripDetailsFragment()
//
//                val bundle = Bundle()
//                bundle.putInt("tripId", trip.id ?: 0)
//                fragment.arguments = bundle
//
//                activity?.supportFragmentManager?.beginTransaction()
//                    ?.replace(R.id.fragmentContainer, fragment)
//                    ?.addToBackStack(null)
//                    ?.commit()
//            }
//
//        }
//    }
//}



package com.example.xmlproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.xmlproject.R
import com.example.xmlproject.data.model.response.TripItem
import com.example.xmlproject.ui.UpcomingTripDetailsFragment
import com.example.xmlproject.utils.DateUtils.formatDateTime

class TripListAdapter(
    private var tripList: List<TripItem>,
    private val onItemClick: (TripItem) -> Unit
) : RecyclerView.Adapter<TripListAdapter.TripViewHolder>() {

    fun updateList(newList: List<TripItem>) {
        tripList = newList.filter { it.isUpcoming == false || it.isUpcoming == null }
        notifyDataSetChanged()
    }

    fun updateUpcomingList(newList: List<TripItem>) {
        tripList = newList.filter { it.isUpcoming == true || it.isUpcoming == null }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_trip, parent, false)
        return TripViewHolder(view)
    }

    override fun getItemCount(): Int = tripList.size

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        holder.bind(tripList[position])
    }

    inner class TripViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvPickup = itemView.findViewById<TextView>(R.id.tvPickupLocation)
        private val tvDropoff = itemView.findViewById<TextView>(R.id.tvDropoffLocation)
        private val tvDate = itemView.findViewById<TextView>(R.id.tvDate)
        private val tvStatus = itemView.findViewById<TextView>(R.id.tvStatus)
        private val tvTotalCost = itemView.findViewById<TextView>(R.id.tvTotalCost)

        fun bind(trip: TripItem) {
            tvPickup.text = "Factory"
            val dropoff = trip.dropoffAddress
            tvDropoff.text = listOfNotNull(dropoff?.line1, dropoff?.line2)
                .joinToString(", ")
                .ifEmpty { "N/A" }

            tvDate.text = formatDateTime(trip.createdAt)
            tvTotalCost.text = (trip.totalCost ?: "N/A").toString()

            val context = itemView.context
            when (trip.status) {
                "Completed" -> tvStatus.setTextColor(ContextCompat.getColor(context, R.color.lime_green))
                "Cancelled" -> tvStatus.setTextColor(ContextCompat.getColor(context, R.color.gray))
                "Pending" -> tvStatus.setTextColor(ContextCompat.getColor(context, R.color.blue))
                else -> tvStatus.setTextColor(ContextCompat.getColor(context, R.color.black))
            }
            tvStatus.text = trip.status ?: "Unknown"

            itemView.setOnClickListener {
                val activity = itemView.context as? AppCompatActivity
                val fragment = UpcomingTripDetailsFragment.newInstance(
                    trip.id ?: 0,
                    trip.isUpcoming == true
                )

                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragmentContainer, fragment)
                    ?.addToBackStack(null)
                    ?.commit()
            }
        }
    }
}
