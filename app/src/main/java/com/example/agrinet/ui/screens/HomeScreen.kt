package com.example.agrinet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.agrinet.data.remote.models.MandiPriceRecord
import com.example.agrinet.ui.components.AgriCard
import com.example.agrinet.ui.components.AgriHeader
import com.example.agrinet.ui.components.StatusBadge
import com.example.agrinet.ui.viewmodel.AgriScreen
import com.example.ui.theme.*

data class QuickActionItem(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val iconBgColor: Color,
    val iconColor: Color,
    val screen: AgriScreen,
    val tag: String
)

@Composable
fun HomeScreen(
    farmerName: String,
    farmLocation: String,
    currentLanguage: String,
    mandiPrices: List<MandiPriceRecord>,
    onNavigate: (AgriScreen) -> Unit,
    onVoiceClick: () -> Unit
) {
    val quickActions = listOf(
        QuickActionItem("Crop Advisory", "AI Recommendations", Icons.Default.Grass, ForestGreenLight, ForestGreen, AgriScreen.CROP_RECOMMENDATION, "action_crop"),
        QuickActionItem("Scan Disease", "Leaf Diagnosis", Icons.Default.CameraAlt, AlertRedLight, AlertRed, AgriScreen.DISEASE_DETECTION, "action_scan"),
        QuickActionItem("ROV-BOT", "Autonomous Robot", Icons.Default.SmartToy, HarvestYellowLight, HarvestOrange, AgriScreen.ROV_BOT, "action_robot"),
        QuickActionItem("Satellite Health", "NDVI Heatmap", Icons.Default.SatelliteAlt, SkyBlueLight, SkyBlue, AgriScreen.SATELLITE_HEALTH, "action_satellite"),
        QuickActionItem("Agri Weather", "Spray & Rain Alert", Icons.Default.WbSunny, HarvestYellowLight, HarvestYellow, AgriScreen.WEATHER, "action_weather"),
        QuickActionItem("Soil Health", "NPK & pH Gauges", Icons.Default.WaterDrop, ForestGreenLight, LeafGreen, AgriScreen.SOIL_HEALTH, "action_soil")
    )

    Scaffold(
        topBar = {
            AgriHeader(
                title = "AgriNet AI",
                subtitle = "Precision Farming • " + farmLocation,
                languageText = currentLanguage,
                onLanguageClick = { onNavigate(AgriScreen.LANGUAGE) },
                onProfileClick = { onNavigate(AgriScreen.PROFILE) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onVoiceClick,
                containerColor = ForestGreen,
                contentColor = PureWhite,
                shape = CircleShape,
                modifier = Modifier
                    .shadow(12.dp, CircleShape)
                    .testTag("fab_voice_assistant")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = "Voice Assistant",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Agri AI",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        containerColor = PureWhite,
        modifier = Modifier.testTag("screen_home")
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Good Morning, " + farmerName + " 🌱",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = ForestGreen
                        )
                        Text(
                            text = "Plot East • 4.5 Acres • Paddy & Chilli",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary
                        )
                    }
                    StatusBadge(text = "ROV ACTIVE", badgeColor = LeafGreen)
                }
            }

            item {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = ForestGreenLight),
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp, RoundedCornerShape(24.dp))
                        .clickable { onNavigate(AgriScreen.WEATHER) }
                        .testTag("card_weather_summary")
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.WbSunny,
                                        contentDescription = "Sunny",
                                        tint = HarvestYellow,
                                        modifier = Modifier.size(32.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = "31°C",
                                        style = MaterialTheme.typography.headlineMedium,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = TextPrimary
                                    )
                                }
                                Text(
                                    text = "Mostly Sunny • Safe to Spray Pesticide",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = ForestGreenDark,
                                    fontWeight = FontWeight.Medium
                                )
                            }

                            Surface(
                                shape = RoundedCornerShape(16.dp),
                                color = PureWhite,
                                shadowElevation = 2.dp
                            ) {
                                Column(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "92%",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = ForestGreen
                                    )
                                    Text(
                                        text = "Crop Health",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = TextSecondary
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(color = PureWhite.copy(alpha = 0.8f))
                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            WeatherMetricItem("Rain Chance", "15%", Icons.Default.WaterDrop, SkyBlue)
                            WeatherMetricItem("Humidity", "62%", Icons.Default.Cloud, LeafGreen)
                            WeatherMetricItem("Soil Moisture", "42%", Icons.Default.Waves, ForestGreen)
                            WeatherMetricItem("Wind Speed", "11 km/h", Icons.Default.Air, HarvestOrange)
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Precision Farming Actions",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "AI Suite",
                        style = MaterialTheme.typography.labelSmall,
                        color = ForestGreen,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    for (i in quickActions.indices step 2) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            val item1 = quickActions[i]
                            QuickActionCard(
                                item = item1,
                                modifier = Modifier.weight(1f),
                                onClick = { onNavigate(item1.screen) }
                            )

                            if (i + 1 < quickActions.size) {
                                val item2 = quickActions[i + 1]
                                QuickActionCard(
                                    item = item2,
                                    modifier = Modifier.weight(1f),
                                    onClick = { onNavigate(item2.screen) }
                                )
                            } else {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }

            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = OffWhite),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, CardBorder, RoundedCornerShape(20.dp))
                        .clickable { onNavigate(AgriScreen.ROV_BOT) }
                        .testTag("banner_rovbot_status")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(HarvestYellowLight),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.SmartToy,
                                contentDescription = "Robot",
                                tint = HarvestOrange,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "ROV-BOT 01 • Precision Weeder",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = "Battery 88% • Solar 48W • RTK GPS Connected",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondary
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Details",
                            tint = ForestGreen
                        )
                    }
                }
            }

            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = PureWhite),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, CardBorder, RoundedCornerShape(20.dp))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Storefront, contentDescription = "Mandi", tint = ForestGreen)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Live Mandi Prices (APMC)",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                            }
                            Text(
                                text = "data.gov.in",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextMuted
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))

                        mandiPrices.take(3).forEach { rate ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = rate.commodity + " (" + rate.market + ")",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Medium,
                                    color = TextPrimary
                                )
                                Text(
                                    text = "₹" + rate.modalPrice + "/Q",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = ForestGreen
                                )
                            }
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = { onNavigate(AgriScreen.GOVERNMENT_SCHEMES) },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.weight(1f).testTag("btn_home_schemes")
                    ) {
                        Text("🏛 Schemes", color = ForestGreen, style = MaterialTheme.typography.labelLarge)
                    }

                    OutlinedButton(
                        onClick = { onNavigate(AgriScreen.COMMUNITY) },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.weight(1f).testTag("btn_home_community")
                    ) {
                        Text("👥 Community", color = ForestGreen, style = MaterialTheme.typography.labelLarge)
                    }

                    OutlinedButton(
                        onClick = { onNavigate(AgriScreen.API_INTEGRATIONS) },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.weight(1f).testTag("btn_home_apis")
                    ) {
                        Text("🔌 APIs", color = ForestGreen, style = MaterialTheme.typography.labelLarge)
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Composable
fun QuickActionCard(
    item: QuickActionItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        modifier = modifier
            .shadow(2.dp, RoundedCornerShape(20.dp))
            .border(1.dp, CardBorder, RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .testTag(item.tag)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(item.iconBgColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.title,
                    tint = item.iconColor,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Text(
                text = item.subtitle,
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondary
            )
        }
    }
}

@Composable
fun WeatherMetricItem(
    label: String,
    value: String,
    icon: ImageVector,
    iconColor: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = iconColor,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = TextSecondary
        )
    }
}
