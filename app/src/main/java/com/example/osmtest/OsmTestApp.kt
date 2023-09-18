package com.example.osmtest

import android.app.Application
import com.example.osmtest.database.PointDatabase

class OsmTestApp : Application() {
    lateinit var database: PointDatabase

    override fun onCreate() {
        super.onCreate()

        database = PointDatabase.getDatabase(this)
    }
}
