package com.example.osmtest.database

import androidx.room.TypeConverter
import com.example.osmtest.entity.Coordinates
import java.util.Date

class CoordinatesListConverter {

    @TypeConverter
    fun fromCoordinatesList(coordinatesList: List<Coordinates>?): String? {
        if (coordinatesList == null) {
            return null
        }
        val coordinatesString = StringBuilder()
        for (coordinates in coordinatesList) {
            coordinatesString.append("${coordinates.latitude},${coordinates.longitude};")
        }
        return coordinatesString.toString()
    }

    @TypeConverter
    fun toCoordinatesList(coordinatesListString: String?): List<Coordinates>? {
        if (coordinatesListString.isNullOrEmpty()) {
            return null
        }
        val coordinatesArray = coordinatesListString.split(";")
        val coordinatesList = mutableListOf<Coordinates>()
        for (coordinatesStr in coordinatesArray) {
            val parts = coordinatesStr.split(",")
            if (parts.size == 2) {
                val latitude = parts[0].toDouble()
                val longitude = parts[1].toDouble()
                coordinatesList.add(Coordinates(latitude, longitude))
            }
        }
        return coordinatesList
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }
}