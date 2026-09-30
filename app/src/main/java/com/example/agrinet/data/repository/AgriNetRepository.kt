package com.example.agrinet.data.repository

import com.example.agrinet.data.local.dao.AgriNetDao
import com.example.agrinet.data.local.entities.CropScanEntity
import com.example.agrinet.data.local.entities.FarmerProfileEntity
import com.example.agrinet.data.local.entities.RovMissionEntity
import com.example.agrinet.data.local.entities.SoilReportEntity
import com.example.agrinet.data.remote.api.ApiClient
import com.example.agrinet.data.remote.models.MandiPriceRecord
import com.example.agrinet.data.remote.models.OpenWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class AgriNetRepository(private val dao: AgriNetDao) {

    val allScans: Flow<List<CropScanEntity>> = dao.getAllCropScans()
    val allSoilReports: Flow<List<SoilReportEntity>> = dao.getAllSoilReports()
    val allMissions: Flow<List<RovMissionEntity>> = dao.getAllRovMissions()
    val farmerProfile: Flow<FarmerProfileEntity?> = dao.getFarmerProfile()

    suspend fun saveCropScan(scan: CropScanEntity): Long = withContext(Dispatchers.IO) {
        dao.insertCropScan(scan)
    }

    suspend fun saveSoilReport(report: SoilReportEntity): Long = withContext(Dispatchers.IO) {
        dao.insertSoilReport(report)
    }

    suspend fun saveRovMission(mission: RovMissionEntity): Long = withContext(Dispatchers.IO) {
        dao.insertRovMission(mission)
    }

    suspend fun saveProfile(profile: FarmerProfileEntity) = withContext(Dispatchers.IO) {
        dao.saveFarmerProfile(profile)
    }

    suspend fun fetchMandiPrices(apiKey: String, state: String = "Telangana"): List<MandiPriceRecord> = withContext(Dispatchers.IO) {
        try {
            if (apiKey.isNotBlank()) {
                val response = ApiClient.dataGovService.getMandiPrices(apiKey = apiKey, state = state)
                if (response.records.isNotEmpty()) return@withContext response.records
            }
        } catch (_: Exception) {}
        return@withContext getSampleMandiRates()
    }

    suspend fun fetchCurrentWeather(apiKey: String, lat: Double, lon: Double): OpenWeatherResponse? = withContext(Dispatchers.IO) {
        try {
            if (apiKey.isNotBlank()) {
                return@withContext ApiClient.openWeatherService.getCurrentWeather(lat, lon, apiKey)
            }
        } catch (_: Exception) {}
        return@withContext null
    }

    private fun getSampleMandiRates(): List<MandiPriceRecord> = listOf(
        MandiPriceRecord("Telangana", "Warangal", "Enumamula", "Chilli (Red)", "Teja", "18500", "16200", "19800"),
        MandiPriceRecord("Telangana", "Nalgonda", "Miryalaguda", "Paddy (Dhan)", "BPT 5204 (Samba)", "2320", "2203", "2380"),
        MandiPriceRecord("Andhra Pradesh", "Guntur", "Guntur Mirchi Yard", "Cotton", "Medium Staple", "7120", "6800", "7450"),
        MandiPriceRecord("Telangana", "Khammam", "Khammam", "Maize (Corn)", "Yellow", "2150", "1950", "2220"),
        MandiPriceRecord("Maharashtra", "Nagpur", "Nagpur APMC", "Soybean", "Yellow", "4850", "4400", "5100"),
        MandiPriceRecord("Punjab", "Ludhiana", "Khanna APMC", "Wheat", "Kalyan Sona", "2275", "2275", "2350")
    )
}
