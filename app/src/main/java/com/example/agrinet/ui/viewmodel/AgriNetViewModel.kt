package com.example.agrinet.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.BuildConfig
import com.example.agrinet.data.local.AppDatabase
import com.example.agrinet.data.local.entities.CropScanEntity
import com.example.agrinet.data.local.entities.FarmerProfileEntity
import com.example.agrinet.data.local.entities.RovMissionEntity
import com.example.agrinet.data.local.entities.SoilReportEntity
import com.example.agrinet.data.remote.models.MandiPriceRecord
import com.example.agrinet.data.repository.AgriNetRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AgriScreen {
    SPLASH,
    LANGUAGE,
    LOGIN,
    HOME,
    CROP_RECOMMENDATION,
    DISEASE_DETECTION,
    ROV_BOT,
    SATELLITE_HEALTH,
    WEATHER,
    SOIL_HEALTH,
    GOVERNMENT_SCHEMES,
    COMMUNITY,
    PROFILE,
    API_INTEGRATIONS
}

data class CropRecommendationResult(
    val bestCrop: String,
    val sowingDateRange: String,
    val expectedYield: String,
    val waterRequirement: String,
    val fertilizerRecommendation: String,
    val estimatedProfitPerAcre: String,
    val suitabilityScore: Int
)

data class DiseaseScanResult(
    val crop: String,
    val diseaseName: String,
    val confidence: Int,
    val severity: String,
    val organicTreatment: String,
    val chemicalTreatment: String,
    val preventionTip: String
)

class AgriNetViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AgriNetRepository

    private val _currentScreen = MutableStateFlow(AgriScreen.SPLASH)
    val currentScreen: StateFlow<AgriScreen> = _currentScreen.asStateFlow()

    private val _bottomNavTab = MutableStateFlow(AgriScreen.HOME)
    val bottomNavTab: StateFlow<AgriScreen> = _bottomNavTab.asStateFlow()

    private val _currentLanguage = MutableStateFlow("English")
    val currentLanguage: StateFlow<String> = _currentLanguage.asStateFlow()

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _farmerName = MutableStateFlow("Ramesh Patel")
    val farmerName: StateFlow<String> = _farmerName.asStateFlow()

    private val _farmLocation = MutableStateFlow("Warangal, Telangana")
    val farmLocation: StateFlow<String> = _farmLocation.asStateFlow()

    private val _mandiPrices = MutableStateFlow<List<MandiPriceRecord>>(emptyList())
    val mandiPrices: StateFlow<List<MandiPriceRecord>> = _mandiPrices.asStateFlow()

    private val _rovBattery = MutableStateFlow(88)
    val rovBattery: StateFlow<Int> = _rovBattery.asStateFlow()

    private val _rovSolarPower = MutableStateFlow(48)
    val rovSolarPower: StateFlow<Int> = _rovSolarPower.asStateFlow()

    private val _rovStatus = MutableStateFlow("STANDBY")
    val rovStatus: StateFlow<String> = _rovStatus.asStateFlow()

    private val _rovMissionProgress = MutableStateFlow(0f)
    val rovMissionProgress: StateFlow<Float> = _rovMissionProgress.asStateFlow()

    private val _rovWeedsEliminated = MutableStateFlow(142)
    val rovWeedsEliminated: StateFlow<Int> = _rovWeedsEliminated.asStateFlow()

    private val _isScanningLeaf = MutableStateFlow(false)
    val isScanningLeaf: StateFlow<Boolean> = _isScanningLeaf.asStateFlow()

    private val _scanResult = MutableStateFlow<DiseaseScanResult?>(null)
    val scanResult: StateFlow<DiseaseScanResult?> = _scanResult.asStateFlow()

    private val _isVoiceAssistantOpen = MutableStateFlow(false)
    val isVoiceAssistantOpen: StateFlow<Boolean> = _isVoiceAssistantOpen.asStateFlow()

    private val _voiceStateText = MutableStateFlow("Tap the microphone or select a prompt below")
    val voiceStateText: StateFlow<String> = _voiceStateText.asStateFlow()

    private val _isVoiceListening = MutableStateFlow(false)
    val isVoiceListening: StateFlow<Boolean> = _isVoiceListening.asStateFlow()

    val scanHistory: StateFlow<List<CropScanEntity>>
    val soilReports: StateFlow<List<SoilReportEntity>>
    val rovMissions: StateFlow<List<RovMissionEntity>>

    init {
        val db = AppDatabase.getDatabase(application)
        repository = AgriNetRepository(db.agriNetDao())

        scanHistory = repository.allScans.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
        soilReports = repository.allSoilReports.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
        rovMissions = repository.allMissions.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

        loadInitialMandiData()
        seedSampleDataIfEmpty()
    }

    fun navigateTo(screen: AgriScreen) {
        _currentScreen.value = screen
        if (screen in listOf(AgriScreen.HOME, AgriScreen.DISEASE_DETECTION, AgriScreen.ROV_BOT, AgriScreen.WEATHER, AgriScreen.PROFILE)) {
            _bottomNavTab.value = screen
        }
    }

    fun setLanguage(language: String) {
        _currentLanguage.value = language
    }

    fun toggleDarkMode() {
        _isDarkMode.value = !_isDarkMode.value
    }

    fun loginUser(phone: String, isGuest: Boolean = false) {
        _isLoggedIn.value = true
        if (isGuest) {
            _farmerName.value = "Kisan Mitra (Guest)"
        }
        navigateTo(AgriScreen.HOME)
    }

    fun startRovMission(type: String) {
        _rovStatus.value = "MISSION_RUNNING"
        viewModelScope.launch {
            for (step in 1..20) {
                if (_rovStatus.value != "MISSION_RUNNING") break
                delay(1000)
                _rovMissionProgress.value = (step * 5).toFloat()
                if (step % 4 == 0) {
                    _rovWeedsEliminated.value += 3
                }
            }
            if (_rovStatus.value == "MISSION_RUNNING") {
                _rovStatus.value = "COMPLETED"
                repository.saveRovMission(
                    RovMissionEntity(
                        missionType = type,
                        status = "COMPLETED",
                        batteryRemaining = _rovBattery.value - 12,
                        durationMinutes = 20,
                        areaCoveredAcres = 1.8f,
                        weedsDetected = _rovWeedsEliminated.value
                    )
                )
            }
        }
    }

    fun pauseRovMission() {
        _rovStatus.value = "PAUSED"
    }

    fun returnRovHome() {
        _rovStatus.value = "RETURNING_HOME"
        viewModelScope.launch {
            delay(3000)
            _rovStatus.value = "STANDBY"
            _rovMissionProgress.value = 0f
        }
    }

    fun performLeafScan(leafType: String) {
        _isScanningLeaf.value = true
        viewModelScope.launch {
            delay(2200)
            val result = when (leafType.lowercase()) {
                "tomato" -> DiseaseScanResult(
                    crop = "Tomato",
                    diseaseName = "Early Blight (Alternaria solani)",
                    confidence = 96,
                    severity = "High",
                    organicTreatment = "Spray Neem oil (5ml/L) or Trichoderma viride culture every 7 days. Ensure good air circulation.",
                    chemicalTreatment = "Mancozeb 75% WP (2.5g/L) or Azoxystrobin 23% SC (1ml/L). Repeat after 10 days if required.",
                    preventionTip = "Avoid overhead sprinkler irrigation; water at base and practice 2-year crop rotation."
                )
                "rice" -> DiseaseScanResult(
                    crop = "Paddy / Rice",
                    diseaseName = "Blast Disease (Magnaporthe oryzae)",
                    confidence = 94,
                    severity = "Medium",
                    organicTreatment = "Apply Pseudomonas fluorescens (10g/L) as foliar spray. Remove infected lower leaves.",
                    chemicalTreatment = "Tricyclazole 75% WP @ 0.6g/L or Isoprothiolane 40% EC @ 1.5ml/L at early tillering stage.",
                    preventionTip = "Avoid excessive Nitrogen application; maintain 5cm balanced standing water."
                )
                "cotton" -> DiseaseScanResult(
                    crop = "Cotton",
                    diseaseName = "Bacterial Blight / Angular Leaf Spot",
                    confidence = 91,
                    severity = "Medium",
                    organicTreatment = "Seed treatment with bio-control agent Bacillus subtilis @ 10g/kg seed.",
                    chemicalTreatment = "Copper Oxychloride 50% WP (3g/L) + Streptocycline (1g/10L).",
                    preventionTip = "Use certified resistant hybrids and destroy crop residue after final picking."
                )
                else -> DiseaseScanResult(
                    crop = "Healthy Plant",
                    diseaseName = "No Pathogen Detected (Vigorous Leaf)",
                    confidence = 98,
                    severity = "None",
                    organicTreatment = "Apply Jeevamrutha or Panchagavya 3% as preventive bio-stimulant.",
                    chemicalTreatment = "No chemical intervention needed. Maintain balanced micronutrient spray.",
                    preventionTip = "Continue regular soil moisture checks and weekly scouting."
                )
            }
            _scanResult.value = result
            _isScanningLeaf.value = false

            repository.saveCropScan(
                CropScanEntity(
                    cropName = result.crop,
                    diseaseName = result.diseaseName,
                    confidence = result.confidence / 100f,
                    severity = result.severity,
                    organicTreatment = result.organicTreatment,
                    chemicalTreatment = result.chemicalTreatment
                )
            )
        }
    }

    fun openVoiceAssistant() {
        _isVoiceAssistantOpen.value = true
        _voiceStateText.value = "Listening in " + _currentLanguage.value + "... Ask anything about your farm!"
    }

    fun closeVoiceAssistant() {
        _isVoiceAssistantOpen.value = false
    }

    fun queryVoiceAssistant(query: String) {
        _isVoiceListening.value = true
        _voiceStateText.value = "Analyzing with AgriNet AI..."
        viewModelScope.launch {
            delay(1500)
            _isVoiceListening.value = false
            _voiceStateText.value = when {
                query.contains("irrigate", ignoreCase = true) ->
                    "Soil moisture is at 42%. With 32°C forecast today, scheduled drip irrigation for 45 mins at 5:00 PM is recommended."
                query.contains("mandi", ignoreCase = true) || query.contains("price", ignoreCase = true) ->
                    "Today's Warangal Mandi red chilli modal price is ₹18,500/quintal. Prices have gained +3.5% this week."
                query.contains("fertilizer", ignoreCase = true) || query.contains("urea", ignoreCase = true) ->
                    "Soil report shows Nitrogen deficiency (210 kg/ha). Apply 25kg Urea + 50kg Neem cake per acre before light irrigation."
                else ->
                    "AgriNet AI recommends scouting your east acre. ROV-BOT completed weeding with 98% accuracy. All systems normal."
            }
        }
    }

    private fun loadInitialMandiData() {
        viewModelScope.launch {
            val prices = repository.fetchMandiPrices(BuildConfig.DATA_GOV_IN_API_KEY)
            _mandiPrices.value = prices
        }
    }

    private fun seedSampleDataIfEmpty() {
        viewModelScope.launch {
            repository.saveSoilReport(
                SoilReportEntity(
                    fieldName = "Plot A - Paddy East",
                    ph = 6.8f,
                    moisture = 42f,
                    nitrogen = 210,
                    phosphorus = 34,
                    potassium = 290,
                    organicCarbon = 0.65f,
                    overallScore = 84,
                    recommendations = "Soil pH is ideal. Supplement organic manure (FYM) 2 tonnes/acre to boost organic carbon."
                )
            )
        }
    }
}
