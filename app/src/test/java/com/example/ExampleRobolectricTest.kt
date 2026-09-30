package com.example

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.agrinet.data.local.entities.CropScanEntity
import com.example.agrinet.data.local.entities.RovMissionEntity
import com.example.agrinet.data.local.entities.SoilReportEntity
import com.example.agrinet.ui.viewmodel.AgriScreen
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleRobolectricTest {

    @Test
    fun testScreenEnumValues() {
        assertEquals("SPLASH", AgriScreen.SPLASH.name)
        assertEquals("HOME", AgriScreen.HOME.name)
        assertEquals("ROV_BOT", AgriScreen.ROV_BOT.name)
        assertEquals("DISEASE_DETECTION", AgriScreen.DISEASE_DETECTION.name)
        assertEquals("CROP_RECOMMENDATION", AgriScreen.CROP_RECOMMENDATION.name)
    }

    @Test
    fun testCropScanEntity() {
        val scan = CropScanEntity(
            cropName = "Tomato",
            diseaseName = "Early Blight",
            confidence = 0.96f,
            severity = "High",
            organicTreatment = "Neem oil spray",
            chemicalTreatment = "Mancozeb"
        )
        assertEquals("Tomato", scan.cropName)
        assertEquals(0.96f, scan.confidence, 0.01f)
        assertEquals("High", scan.severity)
    }

    @Test
    fun testSoilReportEntity() {
        val soil = SoilReportEntity(
            fieldName = "East Field",
            ph = 6.8f,
            moisture = 42f,
            nitrogen = 210,
            phosphorus = 34,
            potassium = 290,
            organicCarbon = 0.65f,
            overallScore = 84,
            recommendations = "Apply organic manure"
        )
        assertEquals(6.8f, soil.ph, 0.01f)
        assertEquals(84, soil.overallScore)
    }

    @Test
    fun testRovMissionEntity() {
        val mission = RovMissionEntity(
            missionType = "Precision Weeding",
            status = "COMPLETED",
            batteryRemaining = 76,
            durationMinutes = 20,
            areaCoveredAcres = 1.8f,
            weedsDetected = 142
        )
        assertEquals("COMPLETED", mission.status)
        assertEquals(142, mission.weedsDetected)
    }
}
