package com.example.osmtest.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_map.mapView
import com.example.osmtest.R
import com.example.osmtest.entity.Coordinates
import com.example.osmtest.entity.NavigationSystem
import com.example.osmtest.entity.PointEntity
import com.example.osmtest.utilities.LocationOverlayManager
import com.example.osmtest.utilities.MapManager
import com.example.osmtest.utilities.OnMarkerClickListener
import com.example.osmtest.utilities.PermissionManager
import com.example.osmtest.viewmodel.MapViewModel
import kotlinx.android.synthetic.main.fragment_map.locationButton
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


class MapFragment : Fragment(R.layout.fragment_map) {

    private val viewModel by viewModels<MapViewModel>()
    private lateinit var mapManager: MapManager
    private lateinit var locationOverlayManager: LocationOverlayManager
    private lateinit var permissionManager: PermissionManager
    private var locationOverlay: MyLocationNewOverlay? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapManager = MapManager(mapView, requireContext())
        permissionManager = PermissionManager(this)
        locationOverlayManager = LocationOverlayManager(mapView)
        locationOverlay = locationOverlayManager.initializeLocationOverlay()

        mapView.setOnLongPressListener { x, y ->
            handleLongPress(x, y)
        }

        locationButton.setOnClickListener { onLocationButtonClick() }

        viewModel.pointsLiveData.observe(viewLifecycleOwner, Observer { points ->
            mapManager.updateMapWithPoints(points)
        })

        mapManager.setOnMarkerClickListener(object : OnMarkerClickListener {
            override fun onMarkerClick(marker: Marker): Boolean {
                val pointInfoFragment = PointInfoBottomSheetFragment()
                pointInfoFragment.show(childFragmentManager, pointInfoFragment.tag)
                return true
            }
        })
    }

    private fun onLocationButtonClick() {
        if (locationOverlay != null && !locationOverlay!!.isMyLocationEnabled) {
            if (!permissionManager.hasLocationPermissions()) {
                permissionManager.requestLocationPermissions()
            }
        } else if (locationOverlay != null) {
            mapView.controller.animateTo(locationOverlay!!.myLocation)
            mapView.controller.setZoom(17.0)
        }
    }


    private fun handleLongPress(x: Float, y: Float) {
        val geoPoint = mapView.projection.fromPixels(x.toInt(), y.toInt())

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Добавить точку")
        val input = EditText(requireContext())
        input.hint = "Имя точки"
        builder.setView(input)

        builder.setPositiveButton("Добавить") { _, _ ->
            val name = input.text.toString()
            if (name.isNotEmpty()) {
                val coordinates = Coordinates(geoPoint.latitude, geoPoint.longitude)
                val point = PointEntity(
                    name = name,
                    coordinatesHistory = mutableListOf(coordinates),
                    navigationSystem = NavigationSystem.GPS,
                    img = R.mipmap.ic_tracker_75dp
                )
                viewModel.addOrUpdatePoint(point)
            }
        }

        builder.setNegativeButton("Отмена") { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }
}