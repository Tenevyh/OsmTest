package com.example.osmtest.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.osmtest.entity.PointEntity


@Database(entities = [PointEntity::class], version = 1)
@TypeConverters(CoordinatesListConverter::class)
abstract class PointDatabase : RoomDatabase() {

    abstract fun pointDao(): PointDao

    companion object {
        @Volatile
        private var INSTANCE: PointDatabase? = null

        fun getDatabase(context: Context): PointDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PointDatabase::class.java,
                    "point_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}