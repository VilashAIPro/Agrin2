package com.example.agrinet.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.agrinet.ui.components.AgriHeader
import com.example.agrinet.ui.components.StatusBadge
import com.example.ui.theme.*

data class ApiInfo(
    val name: String,
    val purpose: String,
    val status: String,
    val endpoint: String,
    val isConnected: Boolean
)

@Composable
fun ApiIntegrationsScreen(
    onBack: () -> Unit
) {
    val apis = listOf(
        ApiInfo("data.gov.in (OGD Platform India)", "Real-time Mandi Market Prices, MSP Rates & APMC arrivals", "CONNECTED", "api.data.gov.in/resource/9ef84268...", true),
        ApiInfo("OpenWeather Agri API", "Micro-climate hourly rainfall, humidity & pesticide spray window", "CONNECTED", "api.openweathermap.org/data/2.5/...", true),
        ApiInfo("Sentinel Hub (ESA Copernicus)", "Sentinel-2 L2A 10m NDVI Heatmap, NDRE & Soil Moisture Radar", "READY", "services.sentinel-hub.com/api/v1/...", true),
        ApiInfo("Google Earth Engine", "Field boundary mapping & Historical multispectral vegetation curves", "READY", "earthengine.googleapis.com/v1/...", true),
        ApiInfo("Gemini AI API (Google)", "Multilingual Agronomist Chat, Voice Assistant & Disease Diagnostic", "ACTIVE", "generativelanguage.googleapis.com/...", true),
        ApiInfo("Firebase Firestore & App Check", "Real-time sync between ROV-BOT field rover & cloud dashboard", "INITIALIZED", "firestore.googleapis.com/v1/...", true)
    )

    Scaffold(
        topBar = {
            AgriHeader(
                title = "API Integrations & Cloud",
                subtitle = "National Portals • Satellite APIs • Google AI",
                showBackButton = true,
                onBackClick = onBack
            )
        },
        containerColor = PureWhite,
        modifier = Modifier.testTag("screen_api_integrations")
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Production API Endpoints",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "AgriNet AI integrates real-time Indian government open data and Earth observation satellites.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }

            items(apis.size) { i ->
                val api = apis[i]
                Card(
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = OffWhite),
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(2.dp, RoundedCornerShape(22.dp))
                        .border(1.dp, CardBorder, RoundedCornerShape(22.dp))
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = api.name,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            StatusBadge(
                                text = api.status,
                                badgeColor = if (api.isConnected) ForestGreen else HarvestYellow
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = api.purpose,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = PureWhite,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = api.endpoint,
                                style = MaterialTheme.typography.labelSmall,
                                color = TextMuted,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
