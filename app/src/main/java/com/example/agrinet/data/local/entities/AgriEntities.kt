package com.example.agrinet.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "crop_scans")
data class CropScanEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val cropName: String,
    val diseaseName: String,
    val confidence: Float,
    val severity: String,
    val organicTreatment: String,
    val chemicalTreatment: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "soil_reports")
data class SoilReportEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val fieldName: String,
    val ph: Float,
    val moisture: Float,
    val nitrogen: Int,
    val phosphorus: Int,
    val potassium: Int,
    val organicCarbon: Float,
    val overallScore: Int,
    val recommendations: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "rov_missions")
data class RovMissionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val missionType: String,
    val status: String,
    val batteryRemaining: Int,
    val durationMinutes: Int,
    val areaCoveredAcres: Float,
    val weedsDetected: Int,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "farmer_profile")
data class FarmerProfileEntity(
    @PrimaryKey
    val id: Int = 1,
    val name: String,
    val phone: String,
    val village: String,
    val state: String,
    val district: String,
    val farmSizeAcres: Float,
    val primaryCrops: String,
    val preferredLanguage: String
)
