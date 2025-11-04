package com.example.xmlproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.xmlproject.databinding.ItemDetailPastTripBinding
import com.example.xmlproject.repository.AuthRepository
import com.example.xmlproject.ui.viewmodel.TripDetailsViewModelFactory
import com.example.xmlproject.viewmodel.TripDetailsViewModel

class UpcomingTripDetailsFragment : Fragment() {

    private var _binding: ItemDetailPastTripBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TripDetailsViewModel
    private var tripId: Int = 0
    private var isUpcoming: Boolean = false

//    companion object {
//        private const val ARG_TRIP_ID = "tripId"
//        private const val ARG_IS_UPCOMING = "isUpcoming"

        companion object {
            private const val ARG_TRIP_ID = "tripId"

            fun newInstance(tripId: Int, isUpcoming: Boolean): UpcomingTripDetailsFragment {
                val fragment = UpcomingTripDetailsFragment()
                val args = Bundle()
                args.putInt("tripId", tripId)
                args.putBoolean("isUpcoming", isUpcoming)
                fragment.arguments = args
                return fragment
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ItemDetailPastTripBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tripId = arguments?.getInt(ARG_TRIP_ID) ?: 0
//        isUpcoming = arguments?.getBoolean(ARG_IS_UPCOMING) ?: false
        val isUpcoming = arguments?.getBoolean("isUpcoming") ?: true

        if (isUpcoming) {
            binding.tripAccepted.visibility = View.GONE
            binding.llBtn.visibility = View.VISIBLE
            binding.tvTitle.text = "Upcoming Trip Details"
        } else {
            binding.llBtn.visibility = View.GONE
            binding.tripAccepted.visibility = View.VISIBLE
            binding.tvTitle.text = "Past Trip Details"
        }

        val repository = AuthRepository()
        val factory = TripDetailsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[TripDetailsViewModel::class.java]

        val token = (activity as? MainActivity)?.getAuthToken() ?: ""

        viewModel.getTripDetails(token, tripId)

        viewModel.tripDetail.observe(viewLifecycleOwner) { detail ->
            binding.profileName.text = detail.receiver?.name ?: "N/A"
            binding.tvDropAddress.text =
                "${detail.dropoffAddress?.line1 ?: ""}, ${detail.dropoffAddress?.line2 ?: ""}"
            binding.tvMaterialCost.text = detail.materialCost?.toString() ?: "N/A"
            binding.tvTransportationCost.text = detail.transportCost?.toString() ?: "N/A"
            binding.tvTotalCost.text = detail.totalCost?.toString() ?: "N/A"
            binding.tvLaborCost.text = detail.totalLabourCost?.toString()?: "N/A"
//            binding.tvDropAddress.text = detail.dropoffAddress?.toString()?:"N/A"
            binding.tvDropAddress.text = detail.dropoffAddress?.getFormattedAddress() ?: "N/A"

//DropoffAddress(line1= Delhi,line2=New Shaheen Bagh,city=New Delhi,state=Delhi,postal_code=110025
            Glide.with(requireContext())
                .load(detail.destinationImage)
                .into(binding.imgDrop)
        }

        viewModel.error.observe(viewLifecycleOwner) { msg ->
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
        }

        binding.backIcon.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
