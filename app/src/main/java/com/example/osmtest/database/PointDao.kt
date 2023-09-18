package com.example.osmtest.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.osmtest.entity.PointEntity

@Dao
interface PointDao {

    @Insert
    suspend fun insertPoint(point: PointEntity)

    @Delete
    suspend fun deletePoint(point: PointEntity)

    @Query("SELECT * FROM points")
    suspend fun getAllPoints(): List<PointEntity>

    @Query("SELECT DISTINCT name FROM points")
    suspend fun getAllPointNames(): List<String>

    @Query("SELECT * FROM points WHERE name = :name")
    suspend fun getPointsByName(name: String): List<PointEntity>

    @Update
    suspend fun updatePoint(point: PointEntity)
}