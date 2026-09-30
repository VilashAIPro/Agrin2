package com.example.agrinet.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.agrinet.data.local.entities.CropScanEntity
import com.example.agrinet.data.local.entities.FarmerProfileEntity
import com.example.agrinet.data.local.entities.RovMissionEntity
import com.example.agrinet.data.local.entities.SoilReportEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AgriNetDao {
    @Query("SELECT * FROM crop_scans ORDER BY timestamp DESC")
    fun getAllCropScans(): Flow<List<CropScanEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCropScan(scan: CropScanEntity): Long

    @Query("SELECT * FROM soil_reports ORDER BY timestamp DESC")
    fun getAllSoilReports(): Flow<List<SoilReportEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSoilReport(report: SoilReportEntity): Long

    @Query("SELECT * FROM rov_missions ORDER BY timestamp DESC")
    fun getAllRovMissions(): Flow<List<RovMissionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRovMission(mission: RovMissionEntity): Long

    @Query("SELECT * FROM farmer_profile WHERE id = 1 LIMIT 1")
    fun getFarmerProfile(): Flow<FarmerProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFarmerProfile(profile: FarmerProfileEntity)
}
