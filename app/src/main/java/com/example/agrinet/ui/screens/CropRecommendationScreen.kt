package com.example.agrinet.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.agrinet.ui.components.AgriCard
import com.example.agrinet.ui.components.AgriHeader
import com.example.agrinet.ui.components.StatusBadge
import com.example.agrinet.ui.viewmodel.CropRecommendationResult
import com.example.ui.theme.*

@Composable
fun CropRecommendationScreen(
    onBack: () -> Unit
) {
    var location by remember { mutableStateOf("Warangal, Telangana") }
    var soilType by remember { mutableStateOf("Black Cotton Soil") }
    var nitrogen by remember { mutableStateOf("210") }
    var phosphorus by remember { mutableStateOf("34") }
    var potassium by remember { mutableStateOf("290") }

    var result by remember {
        mutableStateOf<CropRecommendationResult?>(
            CropRecommendationResult(
                bestCrop = "Chilli (Guntur Teja Hybrid)",
                sowingDateRange = "15 July – 10 August (Kharif)",
                expectedYield = "28 – 34 Quintals / Acre",
                waterRequirement = "600 – 700 mm (Drip Irrigation recommended)",
                fertilizerRecommendation = "N: 120 kg, P2O5: 60 kg, K2O: 60 kg / ha. Apply Zinc Sulphate 25kg/ha.",
                estimatedProfitPerAcre = "₹1,45,000 – ₹1,85,000 / Acre",
                suitabilityScore = 94
            )
        )
    }

    val soilTypes = listOf("Black Cotton Soil", "Red Sandy Loam", "Alluvial Soil", "Clayey Loam", "Laterite")

    Scaffold(
        topBar = {
            AgriHeader(
                title = "Crop Recommendation",
                subtitle = "AI Agronomist • Soil & Climate Match",
                showBackButton = true,
                onBackClick = onBack
            )
        },
        containerColor = PureWhite,
        modifier = Modifier.testTag("screen_crop_recommendation")
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
                    text = "Input Soil & Farm Parameters",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "Provide your NPK values or use latest Soil Health Card readings.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }

            item {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = OffWhite),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, CardBorder, RoundedCornerShape(24.dp))
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = location,
                            onValueChange = { location = it },
                            label = { Text("Farm Location (District, State)") },
                            leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = "Location", tint = ForestGreen) },
                            shape = RoundedCornerShape(16.dp),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth().testTag("input_crop_location")
                        )

                        Text(text = "Soil Type: " + soilType, style = MaterialTheme.typography.labelMedium, color = ForestGreen, fontWeight = FontWeight.Bold)
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            soilTypes.take(3).forEach { st ->
                                val selected = soilType == st
                                FilterChip(
                                    selected = selected,
                                    onClick = { soilType = st },
                                    label = { Text(st, style = MaterialTheme.typography.labelSmall) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = ForestGreen,
                                        selectedLabelColor = PureWhite
                                    )
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            OutlinedTextField(
                                value = nitrogen,
                                onValueChange = { nitrogen = it },
                                label = { Text("N (kg/ha)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier.weight(1f).testTag("input_npk_n")
                            )
                            OutlinedTextField(
                                value = phosphorus,
                                onValueChange = { phosphorus = it },
                                label = { Text("P (kg/ha)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier.weight(1f).testTag("input_npk_p")
                            )
                            OutlinedTextField(
                                value = potassium,
                                onValueChange = { potassium = it },
                                label = { Text("K (kg/ha)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier.weight(1f).testTag("input_npk_k")
                            )
                        }

                        Button(
                            onClick = {
                                result = CropRecommendationResult(
                                    bestCrop = if (soilType.contains("Black")) "Chilli (Guntur Teja Hybrid)" else "Maize (High Yield DMH-849)",
                                    sowingDateRange = "15 July – 10 August (Kharif Season)",
                                    expectedYield = "30 – 35 Quintals / Acre",
                                    waterRequirement = "650 mm (Critical at flowering stage)",
                                    fertilizerRecommendation = "Basal: DAP 50kg + MOP 30kg. Top dress Urea in 3 splits.",
                                    estimatedProfitPerAcre = "₹1,60,000 / Acre",
                                    suitabilityScore = 95
                                )
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = ForestGreen),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("btn_get_recommendation")
                        ) {
                            Icon(Icons.Default.AutoAwesome, contentDescription = "AI", tint = PureWhite)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Get AI Crop Advisory",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = PureWhite
                            )
                        }
                    }
                }
            }

            result?.let { res ->
                item {
                    Text(
                        text = "AI Recommendation Results",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }

                item {
                    Card(
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = ForestGreenLight),
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(3.dp, RoundedCornerShape(24.dp))
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(46.dp)
                                            .clip(CircleShape)
                                            .background(ForestGreen),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Eco,
                                            contentDescription = "Crop",
                                            tint = PureWhite,
                                            modifier = Modifier.size(28.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text(
                                            text = "Best Matching Crop",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = ForestGreenDark,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = res.bestCrop,
                                            style = MaterialTheme.typography.titleLarge,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = TextPrimary
                                        )
                                    }
                                }

                                StatusBadge(
                                    text = res.suitabilityScore.toString() + "% Match",
                                    badgeColor = ForestGreen
                                )
                            }
                        }
                    }
                }

                item {
                    AgriCard {
                        OutputDetailRow("Optimal Sowing Date", res.sowingDateRange, Icons.Default.CalendarToday, ForestGreen)
                        HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = CardBorder)
                        OutputDetailRow("Expected Yield", res.expectedYield, Icons.Default.TrendingUp, LeafGreen)
                        HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = CardBorder)
                        OutputDetailRow("Water Requirement", res.waterRequirement, Icons.Default.WaterDrop, SkyBlue)
                        HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = CardBorder)
                        OutputDetailRow("Estimated Net Profit", res.estimatedProfitPerAcre, Icons.Default.CurrencyRupee, HarvestOrange)
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
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Science, contentDescription = "Fertilizer", tint = ForestGreen)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Recommended Fertilizer Schedule",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = res.fertilizerRecommendation,
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSecondary
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

@Composable
fun OutputDetailRow(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(iconColor.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = title, tint = iconColor, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = TextMuted
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        }
    }
}
