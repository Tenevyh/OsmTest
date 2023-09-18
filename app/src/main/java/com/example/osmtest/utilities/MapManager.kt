package com.example.osmtest.utilities

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.osmtest.entity.PointEntity
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class MapManager(private val mapView: MapView, private val context: Context) {

    private val markers = mutableMapOf<String, List<Marker>>()


    init {
        Configuration.getInstance().load(context, mapView.context.getSharedPreferences("map", 0))
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setBuiltInZoomControls(true)
        mapView.setMultiTouchControls(true)
        mapView.controller.setZoom(15.0)

        val startPoint = GeoPoint(55.682371, 37.581604)
        mapView.controller.setCenter(startPoint)
    }

    var markerClickListener: OnMarkerClickListener? = null

    fun setOnMarkerClickListener(listener: OnMarkerClickListener) {
        markerClickListener = listener
    }

    fun updateMapWithPoints(points: List<PointEntity>) {
        clearMarkers()

        for (point in points) {
            if (point.coordinatesHistory.isNotEmpty()) {
                val coordinatesList = point.coordinatesHistory
                val markerList = mutableListOf<Marker>()

                for (coordinates in coordinatesList) {
                    val geoPoint = GeoPoint(coordinates.latitude, coordinates.longitude)
                    val marker = Marker(mapView)
                    marker.position = geoPoint
                    marker.title = point.name

                    val drawable = ContextCompat.getDrawable(context, point.img)
                    marker.icon = BitmapDrawable(context.resources, drawable?.toBitmap())

                    marker.setOnMarkerClickListener { _, _ ->
                        markerClickListener?.onMarkerClick(marker)
                        true
                    }

                    markerList.add(marker)
                }

                markers[point.name] = markerList
                mapView.overlays.addAll(markerList)


            markers[point.name] = markerList
        }
    }

        mapView.invalidate()
    }

    private fun clearMarkers() {
        mapView.overlays.clear()
        markers.clear()
        mapView.invalidate()
    }
}

interface OnMarkerClickListener {
    fun onMarkerClick(marker: Marker): Boolean
}
