package com.example.xmlproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.xmlproject.R
import com.example.xmlproject.databinding.FragmentHomeBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class HomePageFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var googleMap: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)

        binding.onlineSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.offlineText.text = "Online"
                binding.offlineText.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.black)
                )
                binding.tvStatus.text = "You Are Now Online!"
                binding.tvStatus.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.lime_green)
                )
            } else {
                binding.offlineText.text = "Offline"
                binding.offlineText.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.black)
                )
                binding.tvStatus.text = "Get online to start receiving requests"
                binding.tvStatus.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.black)
                )
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        val currentLocation = LatLng(18.5204, 73.8567)
        googleMap?.addMarker(MarkerOptions().position(currentLocation).title("You are here"))
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 14f))
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.mapView.onDestroy()
        _binding = null
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }
}
