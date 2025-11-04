package com.example.xmlproject.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xmlproject.R
import com.example.xmlproject.adapter.TripListAdapter
import com.example.xmlproject.data.model.response.TripItem
import com.example.xmlproject.databinding.FragmentUpcomingTripBinding
import com.example.xmlproject.repository.AuthRepository
import com.example.xmlproject.viewmodel.TripListViewModel
import com.example.xmlproject.viewmodel.TripListViewModelFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class UpcomingTripFragment : Fragment() {

    private var _binding: FragmentUpcomingTripBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TripListViewModel
    private lateinit var adapter: TripListAdapter
    private var fullTripList: List<TripItem> = emptyList()

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingTripBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = AuthRepository()
        val factory = TripListViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[TripListViewModel::class.java]

        setupRecyclerView()
        setupRecyclerViewUpcomingTripDetails()
        setupUI()

        val token = (activity as? MainActivity)?.getAuthToken() ?: ""
        viewModel.getTripList(token, true)

//        viewModel.upcomingTrips.observe(viewLifecycleOwner) { trips ->
//            adapter.updateUpcomingList(trips)
//        }

        viewModel.upcomingTrips.observe(viewLifecycleOwner) { trips ->
            fullTripList = trips
            adapter.updateUpcomingList(trips)
        }


        viewModel.error.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupUI() {
        binding.fromDate.setOnClickListener {
            showDatePicker { selectedDate ->
                binding.tvFromDate.text = selectedDate
                filterTrips()
            }
        }

        binding.toDate.setOnClickListener {
            showDatePicker { selectedDate ->
                binding.tvToDate.text = selectedDate
                filterTrips()
            }
        }

        binding.btnBack.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, TripListFragment())
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

    private fun filterTrips() {
        val fromDateStr = binding.tvFromDate.text.toString()
        val toDateStr = binding.tvToDate.text.toString()

        if (fromDateStr.isEmpty() || toDateStr.isEmpty()) {
            adapter.updateUpcomingList(fullTripList)
            return
        }

        try {
            val fromDate = dateFormat.parse(fromDateStr)
            val toDate = dateFormat.parse(toDateStr)

            if (fromDate != null && toDate != null) {
                val filteredList = fullTripList.filter { trip ->
                    trip.createdAt?.let { dateStr ->
                        try {
                            val tripDateRaw = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(dateStr)
                            val tripDate = dateFormat.parse(SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(tripDateRaw))

                            tripDate != null && !tripDate.before(fromDate) && !tripDate.after(toDate)
                        } catch (e: Exception) {
                            false
                        }
                    } ?: false
                }

                adapter.updateUpcomingList(filteredList)
            } else {
                adapter.updateUpcomingList(fullTripList)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            adapter.updateUpcomingList(fullTripList)
        }
    }


    private fun setupRecyclerView() {
        adapter = TripListAdapter(emptyList()) { trip ->
            Toast.makeText(requireContext(), "Clicked: ${trip.status}", Toast.LENGTH_SHORT).show()
        }
        binding.recyclerViewUpcoming.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewUpcoming.adapter = adapter
    }


    private fun setupRecyclerViewUpcomingTripDetails() {
        adapter = TripListAdapter(emptyList()) { trip ->
            val detailFragment = UpcomingTripDetailsFragment.newInstance(
                trip.id ?: 0,
                trip.isUpcoming ?: false
            )
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, detailFragment)
                .addToBackStack(null)
                .commit()
        }
        binding.recyclerViewUpcoming.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewUpcoming.adapter = adapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
