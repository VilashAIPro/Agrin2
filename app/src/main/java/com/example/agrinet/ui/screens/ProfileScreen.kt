package com.example.agrinet.ui.screens

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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.agrinet.ui.components.AgriCard
import com.example.agrinet.ui.components.AgriHeader
import com.example.agrinet.ui.components.StatusBadge
import com.example.agrinet.ui.viewmodel.AgriScreen
import com.example.ui.theme.*

@Composable
fun ProfileScreen(
    farmerName: String,
    farmLocation: String,
    currentLanguage: String,
    isDarkMode: Boolean,
    scansCount: Int,
    soilReportsCount: Int,
    missionsCount: Int,
    onToggleDarkMode: () -> Unit,
    onLanguageClick: () -> Unit,
    onNavigate: (AgriScreen) -> Unit,
    onBack: () -> Unit
) {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var highContrastEnabled by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            AgriHeader(
                title = "Farmer Profile & Settings",
                subtitle = "AgriNet AI Ecosystem ID: #KA-8941",
                showBackButton = true,
                onBackClick = onBack
            )
        },
        containerColor = PureWhite,
        modifier = Modifier.testTag("screen_profile")
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
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = ForestGreenLight),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(ForestGreen),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Farmer",
                                tint = PureWhite,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = farmerName,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = farmLocation,
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSecondary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            StatusBadge(text = "VERIFIED KISAN", badgeColor = LeafGreen)
                        }
                    }
                }
            }

            item {
                AgriCard {
                    Text(
                        text = "Farm Holding Details",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ProfileStatItem("Land Holding", "4.5 Acres", Icons.Default.Landscape)
                        ProfileStatItem("Soil Card", "Updated Aug 2026", Icons.Default.Badge)
                        ProfileStatItem("ROV Rover", "ROV-BOT #01", Icons.Default.SmartToy)
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
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Storage, contentDescription = "Database", tint = ForestGreen)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Offline SQLite & Room Storage",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                            }
                            StatusBadge(text = "SYNCED", badgeColor = ForestGreen)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Locally cached: " + scansCount + " leaf scans • " + soilReportsCount + " soil cards • " + missionsCount + " rover logs.",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )
                    }
                }
            }

            item {
                Text(
                    text = "App Settings & Accessibility",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }

            item {
                Card(
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = PureWhite),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, CardBorder, RoundedCornerShape(22.dp))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onLanguageClick() }
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Translate, contentDescription = "Language", tint = ForestGreen)
                                Spacer(modifier = Modifier.width(12.dp))
                                Text("Language / భాష", style = MaterialTheme.typography.bodyLarge, color = TextPrimary)
                            }
                            Text(currentLanguage, style = MaterialTheme.typography.labelLarge, color = ForestGreen, fontWeight = FontWeight.Bold)
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), color = CardBorder)

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.DarkMode, contentDescription = "Dark Mode", tint = ForestGreen)
                                Spacer(modifier = Modifier.width(12.dp))
                                Text("Dark Mode", style = MaterialTheme.typography.bodyLarge, color = TextPrimary)
                            }
                            Switch(
                                checked = isDarkMode,
                                onCheckedChange = { onToggleDarkMode() },
                                colors = SwitchDefaults.colors(checkedThumbColor = ForestGreen, checkedTrackColor = ForestGreenLight)
                            )
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), color = CardBorder)

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = ForestGreen)
                                Spacer(modifier = Modifier.width(12.dp))
                                Text("Pest & Weather Push Alerts", style = MaterialTheme.typography.bodyLarge, color = TextPrimary)
                            }
                            Switch(
                                checked = notificationsEnabled,
                                onCheckedChange = { notificationsEnabled = it },
                                colors = SwitchDefaults.colors(checkedThumbColor = ForestGreen, checkedTrackColor = ForestGreenLight)
                            )
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), color = CardBorder)

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Visibility, contentDescription = "High Contrast", tint = HarvestOrange)
                                Spacer(modifier = Modifier.width(12.dp))
                                Text("High Contrast / Large Font", style = MaterialTheme.typography.bodyLarge, color = TextPrimary)
                            }
                            Switch(
                                checked = highContrastEnabled,
                                onCheckedChange = { highContrastEnabled = it },
                                colors = SwitchDefaults.colors(checkedThumbColor = HarvestOrange, checkedTrackColor = HarvestYellowLight)
                            )
                        }
                    }
                }
            }

            item {
                OutlinedButton(
                    onClick = { onNavigate(AgriScreen.API_INTEGRATIONS) },
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier.fillMaxWidth().height(50.dp).testTag("btn_profile_apis")
                ) {
                    Icon(Icons.Default.Api, contentDescription = "APIs", tint = ForestGreen)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Manage Cloud & Gov APIs (data.gov.in, Weather, Sentinel)", fontWeight = FontWeight.Bold, color = ForestGreen)
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun ProfileStatItem(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = title, tint = ForestGreen, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.height(4.dp))
        Text(value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextPrimary)
        Text(title, style = MaterialTheme.typography.labelSmall, color = TextMuted)
    }
}
