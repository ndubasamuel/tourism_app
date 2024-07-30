package example.application.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.util.Log
import example.application.model.UseData
import example.application.R
import example.application.model.source.Data
import example.application.databinding.FragmentLandingBinding
import example.application.view.adapters.CabsAdapter
import example.application.view.adapters.TopScrollAdapter
import java.io.IOException


class LandingFragment : Fragment(), OnMapReadyCallback, TopScrollAdapter.OnItemClickListener {

    private lateinit var binding: FragmentLandingBinding
    private lateinit var googleMap: GoogleMap
    private lateinit var startDestination: EditText
    private lateinit var dropOff: EditText
    private lateinit var searchButton: Button

    private lateinit var fusedLocationClient: FusedLocationProviderClient


    private lateinit var topScrollAdapter: TopScrollAdapter
    private lateinit var cabsAdapter: CabsAdapter
    private var topItems: ArrayList<UseData.TopScroll> = ArrayList()
    private var cabItems: ArrayList<UseData.CabScroll> = ArrayList()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentLandingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        startDestination = binding.startDestination
        dropOff = binding.dropOff
        searchButton = binding.searchButton

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        searchButton.setOnClickListener {
            searchLocations()
        }

        val dataSource = Data()
        topItems = dataSource.strings()
        cabItems = dataSource.cabStrings()

        setUpRecyclerViewAdapter()
    }


    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        val initialLocation = LatLng(-1.285790, 36.820030)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 10f))


        enableMyLocation()
    }

    private fun enableMyLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        googleMap.isMyLocationEnabled = true
        getLastKnownLocation()
    }


    private fun getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 10f))
                }
            }
        }
    }

    private fun searchLocations() {
        val startLocation = startDestination.text.toString()
        val dropOffLocation = dropOff.text.toString()

        val startLatLng = geocodeAddress(startLocation)
        val dropOffLatLng = geocodeAddress(dropOffLocation)

        googleMap.clear()
        googleMap.addMarker(MarkerOptions().position(startLatLng).title("Start"))
        googleMap.addMarker(MarkerOptions().position(dropOffLatLng).title("Drop Off"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startLatLng, 10f))


    }
    private fun geocodeAddress(address: String): LatLng {
        val geocoder = Geocoder(requireContext())
        return try {
            val addresses: MutableList<Address>? = geocoder.getFromLocationName(address, 1)
            if (addresses?.isNotEmpty() == true) {
                val location: Address = addresses[0]
                LatLng(location.latitude, location.longitude)
            } else {
                LatLng(-1.285790, 36.820030) // Default location if address not found
            }
        } catch (e: IOException) {
            Log.e("Landing Fragment", "GeoAddress Error")
            e.printStackTrace()
            LatLng(-1.285790, 36.820030) // Default location if there's an error
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation()
            }
        }
    }


    private fun setUpRecyclerViewAdapter() {
//        RecyclerView
        val topRecyclerView = binding.topScrollRecycler
        topRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false )
//        Adapter
        topScrollAdapter =TopScrollAdapter(topItems, this)
        topRecyclerView.adapter = topScrollAdapter


        val cabRecyclerView = binding.cabRecyclerView
        cabRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        cabsAdapter = CabsAdapter(cabItems)
        cabRecyclerView.adapter = cabsAdapter
    }

    override fun onItemClick(position: Int) {
        val categoryName = topItems[position].text
        when (categoryName) {
            "CABS" -> {

            }
            "ACCOMMODATIONS" -> {
                findNavController().navigate(R.id.action_landingFragment2_to_accommodationFragment)
            }
            "SAFARIS" -> {
                findNavController().navigate(R.id.action_landingFragment2_to_safarisFragment)

            }
            "$#EXCHANGE" -> {
                findNavController().navigate(R.id.action_landingFragment2_to_currencyFragment)

            }
            "FLIGHTS" -> {
                findNavController().navigate(R.id.action_landingFragment2_to_flightsFragment)

            }
        }

    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

}