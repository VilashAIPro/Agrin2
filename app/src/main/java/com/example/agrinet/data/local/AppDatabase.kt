package com.example.agrinet.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.agrinet.data.local.dao.AgriNetDao
import com.example.agrinet.data.local.entities.CropScanEntity
import com.example.agrinet.data.local.entities.FarmerProfileEntity
import com.example.agrinet.data.local.entities.RovMissionEntity
import com.example.agrinet.data.local.entities.SoilReportEntity

@Database(
    entities = [
        CropScanEntity::class,
        SoilReportEntity::class,
        RovMissionEntity::class,
        FarmerProfileEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun agriNetDao(): AgriNetDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "agrinet_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
