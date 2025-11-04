package com.example.xmlproject.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xmlproject.R
import com.example.xmlproject.adapter.TripListAdapter
import com.example.xmlproject.data.model.response.TripItem
import com.example.xmlproject.databinding.FragmentTripListBinding
import com.example.xmlproject.repository.AuthRepository
import com.example.xmlproject.utils.DateUtils.formatDateTime
import com.example.xmlproject.viewmodel.TripListViewModel
import com.example.xmlproject.viewmodel.TripListViewModelFactory
import java.util.Calendar

class TripListFragment : Fragment() {

    private var _binding: FragmentTripListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TripListViewModel
    private lateinit var tripListAdapter: TripListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTripListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = AuthRepository()
        val factory = TripListViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[TripListViewModel::class.java]

        setupRecyclerView()
        setupUI()

        val token = (activity as? MainActivity)?.getAuthToken() ?: ""
        viewModel.getTripList(token, isUpcoming = true)
        viewModel.getTripList(token, isUpcoming = false)

        setupObservers()
    }

    private fun setupRecyclerView() {
        tripListAdapter = TripListAdapter(emptyList()) { trip ->
            openTripDetails(trip)
        }

        binding.recyclerViewTrips.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = tripListAdapter
        }
    }

    private fun openTripDetails(trip: TripItem) {
        val detailFragment = UpcomingTripDetailsFragment.newInstance(
            trip.id ?: 0,
            trip.isUpcoming ?: false
        )

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, detailFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupObservers() {
        viewModel.upcomingTrips.observe(viewLifecycleOwner) { upcomingList ->
            if (!upcomingList.isNullOrEmpty()) {
                val topTrip = upcomingList.first()
                bindUpcomingTrip(topTrip)
            } else {
                binding.cardUpcoming.visibility = View.GONE
            }
        }

        viewModel.pastTrips.observe(viewLifecycleOwner) { pastTrips ->
            tripListAdapter.updateList(pastTrips)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupUI() {
        binding.layoutFromDate.setOnClickListener {
            showDatePicker { selectedDate ->
                binding.tvFromDate.text = selectedDate
            }
        }

        binding.layoutToDate.setOnClickListener {
            showDatePicker { selectedDate ->
                binding.tvToDate.text = selectedDate
            }
        }

        binding.layoutAll.setOnClickListener { view ->
            showDropdown(view)
        }

        binding.btnBack.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, HomePageFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.cardUpcoming.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, UpcomingTripFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.tvViewMore.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, UpcomingTripFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun showDatePicker(onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(requireContext(), { _, y, m, d ->
            onDateSelected("$d/${m + 1}/$y")
        }, year, month, day)

        datePicker.show()
    }

    private fun showDropdown(view: View) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.setOnMenuItemClickListener { item ->
            binding.tvFilter.text = item.title
            true
        }
        popupMenu.show()
    }

    private fun bindUpcomingTrip(trip: TripItem) {
        binding.cardUpcoming.visibility = View.VISIBLE

        binding.tvUpcomingPickup.text = "Factory"

        val dropoff = trip.dropoffAddress
        binding.tvUpcomingDropoff.text = listOfNotNull(dropoff?.line1, dropoff?.line2)
            .joinToString(", ")
            .ifEmpty { "N/A" }

        binding.tvUpcomingDate.text = formatDateTime(trip.createdAt)
        binding.tvUpcomingStatus.text = trip.status ?: "Unknown"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
