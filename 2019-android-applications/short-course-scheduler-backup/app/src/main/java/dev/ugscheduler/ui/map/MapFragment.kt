package dev.ugscheduler.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.tasks.Tasks
import dev.ugscheduler.R
import dev.ugscheduler.databinding.MapFragmentBinding
import dev.ugscheduler.shared.util.debugger
import dev.ugscheduler.shared.util.toLatLng
import dev.ugscheduler.util.MainNavigationFragment
import kotlinx.coroutines.launch

class MapFragment : MainNavigationFragment(), OnMapReadyCallback {
    private lateinit var map: GoogleMap

    override fun onMapReady(googleMap: GoogleMap?) {
        if (googleMap != null) {
            // Initialize map
            map = googleMap

            // Apply settings to map
            map.apply {
                setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                        requireContext(),
                        R.raw.map_style_day
                    )
                )
            }

            // Get user location live updates
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) updateUserLocationWithData() else {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ), RC_LOC_PERM
                )
            }
        }
    }

    private fun updateUserLocationWithData() {
        debugger("Updating user's current location information")
        ioScope.launch {
            // Get user's last location asynchronously
            val location =
                Tasks.await(LocationServices.getFusedLocationProviderClient(requireActivity()).lastLocation)
            debugger("Current location is: ${location.toLatLng()}")

            uiScope.launch {
                // Animate camera target to user's location
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(location.toLatLng(), 16f), 550, null)

                // todo: show button to help them navigate to department for lectures
            }
        }
    }

    private lateinit var binding: MapFragmentBinding
    private lateinit var viewModel: MapViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MapFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MapViewModel::class.java)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.google_map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RC_LOC_PERM && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            updateUserLocationWithData()
    }

    companion object {
        private const val RC_LOC_PERM = 2
    }

}
