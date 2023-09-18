package com.example.osmtest.utilities

import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.osmtest.R
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class LocationOverlayManager(private val mapView: MapView) {

    private lateinit var locationOverlay: MyLocationNewOverlay

    fun initializeLocationOverlay(): MyLocationNewOverlay {
        locationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(mapView.context), mapView)
        locationOverlay.enableMyLocation()
        mapView.overlays.add(locationOverlay)
        locationOverlay.disableFollowLocation()
        locationOverlay.setPersonIcon(ContextCompat.getDrawable(mapView.context, R.mipmap.ic_mylocation_55dp)?.toBitmap())
        mapView.invalidate()
        return locationOverlay
    }
}