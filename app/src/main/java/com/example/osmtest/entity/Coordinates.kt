package com.example.osmtest.entity

import java.util.Date

data class Coordinates(
    val latitude: Double,
    val longitude: Double,
    val date: Date = Date()
)
