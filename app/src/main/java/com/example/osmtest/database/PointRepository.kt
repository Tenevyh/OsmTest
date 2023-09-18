package com.example.osmtest.database

import com.example.osmtest.entity.PointEntity

class PointRepository(private val pointDao: PointDao) {

    suspend fun addOrUpdatePoint(point: PointEntity) {
        val existingPoints = pointDao.getPointsByName(point.name)
        if (existingPoints.isNotEmpty()) {
            val existingPoint = existingPoints[0]
            val updatedCoordinates = existingPoint.coordinatesHistory + point.coordinatesHistory
            val updatedPoint = existingPoint.copy(coordinatesHistory = updatedCoordinates)
            pointDao.updatePoint(updatedPoint)
        } else {
            pointDao.insertPoint(point)
        }
    }

    suspend fun deletePoint(point: PointEntity) {
        pointDao.deletePoint(point)
    }

    suspend fun getPointsByName(name: String): List<PointEntity> {
        return pointDao.getPointsByName(name)
    }

    suspend fun getAllPoints(): List<PointEntity> {
        return pointDao.getAllPoints()
    }
}