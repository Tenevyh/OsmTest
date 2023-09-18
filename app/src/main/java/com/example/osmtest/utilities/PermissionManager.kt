package com.example.osmtest.utilities

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

class PermissionManager(private val fragment: Fragment) {

    private val LOCATION_PERMISSION_REQUEST_CODE = 123

    fun hasLocationPermissions(): Boolean {
        val fineLocationPermissionGranted = ActivityCompat.checkSelfPermission(
            fragment.requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val coarseLocationPermissionGranted = ActivityCompat.checkSelfPermission(
            fragment.requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        return fineLocationPermissionGranted || coarseLocationPermissionGranted
    }

    fun requestLocationPermissions() {
        ActivityCompat.requestPermissions(
            fragment.requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }
}