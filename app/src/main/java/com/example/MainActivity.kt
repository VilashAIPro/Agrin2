package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.agrinet.ui.components.AgriBottomNavigation
import com.example.agrinet.ui.screens.*
import com.example.agrinet.ui.viewmodel.AgriNetViewModel
import com.example.agrinet.ui.viewmodel.AgriScreen
import com.example.ui.theme.AgriNetAITheme

class MainActivity : ComponentActivity() {

    private val viewModel: AgriNetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDarkMode by viewModel.isDarkMode.collectAsState()

            AgriNetAITheme(darkTheme = isDarkMode) {
                AgriNetApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun AgriNetApp(viewModel: AgriNetViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val bottomNavTab by viewModel.bottomNavTab.collectAsState()
    val currentLanguage by viewModel.currentLanguage.collectAsState()
    val isDarkMode by viewModel.isDarkMode.collectAsState()
    val farmerName by viewModel.farmerName.collectAsState()
    val farmLocation by viewModel.farmLocation.collectAsState()
    val mandiPrices by viewModel.mandiPrices.collectAsState()

    val rovBattery by viewModel.rovBattery.collectAsState()
    val rovSolarPower by viewModel.rovSolarPower.collectAsState()
    val rovStatus by viewModel.rovStatus.collectAsState()
    val rovProgress by viewModel.rovMissionProgress.collectAsState()
    val rovWeedsCount by viewModel.rovWeedsEliminated.collectAsState()

    val isScanningLeaf by viewModel.isScanningLeaf.collectAsState()
    val scanResult by viewModel.scanResult.collectAsState()

    val isVoiceOpen by viewModel.isVoiceAssistantOpen.collectAsState()
    val isVoiceListening by viewModel.isVoiceListening.collectAsState()
    val voiceStateText by viewModel.voiceStateText.collectAsState()

    val scanHistory by viewModel.scanHistory.collectAsState()
    val soilReports by viewModel.soilReports.collectAsState()
    val rovMissions by viewModel.rovMissions.collectAsState()

    if (currentScreen != AgriScreen.HOME && currentScreen != AgriScreen.SPLASH) {
        BackHandler {
            viewModel.navigateTo(AgriScreen.HOME)
        }
    }

    val showBottomBar = currentScreen in listOf(
        AgriScreen.HOME,
        AgriScreen.DISEASE_DETECTION,
        AgriScreen.ROV_BOT,
        AgriScreen.WEATHER,
        AgriScreen.PROFILE
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                AgriBottomNavigation(
                    currentTab = bottomNavTab,
                    onTabSelected = { selectedTab ->
                        viewModel.navigateTo(selectedTab)
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentScreen) {
                AgriScreen.SPLASH -> {
                    SplashScreen(
                        onContinue = { viewModel.navigateTo(AgriScreen.LANGUAGE) }
                    )
                }

                AgriScreen.LANGUAGE -> {
                    LanguageScreen(
                        currentLanguage = currentLanguage,
                        onLanguageSelected = { viewModel.setLanguage(it) },
                        onContinue = { viewModel.navigateTo(AgriScreen.LOGIN) }
                    )
                }

                AgriScreen.LOGIN -> {
                    LoginScreen(
                        onLoginSuccess = { phone, isGuest ->
                            viewModel.loginUser(phone, isGuest)
                        }
                    )
                }

                AgriScreen.HOME -> {
                    HomeScreen(
                        farmerName = farmerName,
                        farmLocation = farmLocation,
                        currentLanguage = currentLanguage,
                        mandiPrices = mandiPrices,
                        onNavigate = { screen -> viewModel.navigateTo(screen) },
                        onVoiceClick = { viewModel.openVoiceAssistant() }
                    )
                }

                AgriScreen.CROP_RECOMMENDATION -> {
                    CropRecommendationScreen(
                        onBack = { viewModel.navigateTo(AgriScreen.HOME) }
                    )
                }

                AgriScreen.DISEASE_DETECTION -> {
                    DiseaseDetectionScreen(
                        isScanning = isScanningLeaf,
                        scanResult = scanResult,
                        onScanRequested = { sample -> viewModel.performLeafScan(sample) },
                        onBack = { viewModel.navigateTo(AgriScreen.HOME) }
                    )
                }

                AgriScreen.ROV_BOT -> {
                    RovBotScreen(
                        battery = rovBattery,
                        solarPower = rovSolarPower,
                        status = rovStatus,
                        progress = rovProgress,
                        weedsCount = rovWeedsCount,
                        onStartMission = { type -> viewModel.startRovMission(type) },
                        onPauseMission = { viewModel.pauseRovMission() },
                        onReturnHome = { viewModel.returnRovHome() },
                        onBack = { viewModel.navigateTo(AgriScreen.HOME) }
                    )
                }

                AgriScreen.SATELLITE_HEALTH -> {
                    SatelliteHealthScreen(
                        onBack = { viewModel.navigateTo(AgriScreen.HOME) }
                    )
                }

                AgriScreen.WEATHER -> {
                    WeatherScreen(
                        location = farmLocation,
                        onBack = { viewModel.navigateTo(AgriScreen.HOME) }
                    )
                }

                AgriScreen.SOIL_HEALTH -> {
                    SoilHealthScreen(
                        onBack = { viewModel.navigateTo(AgriScreen.HOME) }
                    )
                }

                AgriScreen.GOVERNMENT_SCHEMES -> {
                    GovernmentSchemesScreen(
                        onBack = { viewModel.navigateTo(AgriScreen.HOME) }
                    )
                }

                AgriScreen.COMMUNITY -> {
                    CommunityScreen(
                        onBack = { viewModel.navigateTo(AgriScreen.HOME) },
                        onAskAi = { query -> viewModel.queryVoiceAssistant(query) }
                    )
                }

                AgriScreen.PROFILE -> {
                    ProfileScreen(
                        farmerName = farmerName,
                        farmLocation = farmLocation,
                        currentLanguage = currentLanguage,
                        isDarkMode = isDarkMode,
                        scansCount = scanHistory.size,
                        soilReportsCount = soilReports.size,
                        missionsCount = rovMissions.size,
                        onToggleDarkMode = { viewModel.toggleDarkMode() },
                        onLanguageClick = { viewModel.navigateTo(AgriScreen.LANGUAGE) },
                        onNavigate = { screen -> viewModel.navigateTo(screen) },
                        onBack = { viewModel.navigateTo(AgriScreen.HOME) }
                    )
                }

                AgriScreen.API_INTEGRATIONS -> {
                    ApiIntegrationsScreen(
                        onBack = { viewModel.navigateTo(AgriScreen.HOME) }
                    )
                }
            }

            VoiceAssistantDialog(
                isOpen = isVoiceOpen,
                isListening = isVoiceListening,
                stateText = voiceStateText,
                currentLanguage = currentLanguage,
                onQuery = { query -> viewModel.queryVoiceAssistant(query) },
                onDismiss = { viewModel.closeVoiceAssistant() }
            )
        }
    }
}
