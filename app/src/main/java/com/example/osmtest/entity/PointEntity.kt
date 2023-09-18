package com.example.osmtest.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.osmtest.R

@Entity(tableName = "points")
data class PointEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val coordinatesHistory: List<Coordinates>,
    val navigationSystem: NavigationSystem,
    var img: Int = R.drawable.ic_launcher_foreground
)