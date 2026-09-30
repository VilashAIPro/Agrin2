package com.example.agrinet.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.border
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.agrinet.ui.components.AgriCard
import com.example.agrinet.ui.components.AgriHeader
import com.example.agrinet.ui.components.CircularGauge
import com.example.agrinet.ui.components.StatusBadge
import com.example.ui.theme.*

@Composable
fun SoilHealthScreen(
    onBack: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "PlantBreathe")
    val plantScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Breathe"
    )

    Scaffold(
        topBar = {
            AgriHeader(
                title = "Soil Health Analytics",
                subtitle = "IoT Sensor + Soil Health Card Lab Test",
                showBackButton = true,
                onBackClick = onBack
            )
        },
        containerColor = PureWhite,
        modifier = Modifier.testTag("screen_soil_health")
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
                    shape = RoundedCornerShape(26.dp),
                    colors = CardDefaults.cardColors(containerColor = ForestGreenLight),
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(3.dp, RoundedCornerShape(26.dp))
                ) {
                    Column(
                        modifier = Modifier.padding(22.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Plot A • East Paddy Field",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            StatusBadge(text = "HEALTHY SOIL", badgeColor = ForestGreen)
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        Box(
                            modifier = Modifier
                                .size(90.dp)
                                .scale(plantScale)
                                .clip(CircleShape)
                                .background(ForestGreen),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Park,
                                contentDescription = "Growing Plant",
                                tint = PureWhite,
                                modifier = Modifier.size(54.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "84 / 100",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = ForestGreenDark
                        )
                        Text(
                            text = "Overall Soil Fertility Index (Good Condition)",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary
                        )
                    }
                }
            }

            item {
                Text(
                    text = "Key Soil Chemistry Gauges",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Card(
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(containerColor = OffWhite),
                        modifier = Modifier
                            .weight(1f)
                            .border(1.dp, CardBorder, RoundedCornerShape(22.dp))
                    ) {
                        CircularGauge(
                            value = 6.8f,
                            maxValue = 14f,
                            label = "Soil pH Level",
                            statusText = "Optimal (Neutral)",
                            gaugeColor = ForestGreen
                        )
                    }

                    Card(
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(containerColor = OffWhite),
                        modifier = Modifier
                            .weight(1f)
                            .border(1.dp, CardBorder, RoundedCornerShape(22.dp))
                    ) {
                        CircularGauge(
                            value = 42f,
                            maxValue = 100f,
                            label = "Moisture Level",
                            unit = "%",
                            statusText = "Adequate",
                            gaugeColor = SkyBlue
                        )
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Card(
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(containerColor = OffWhite),
                        modifier = Modifier
                            .weight(1f)
                            .border(1.dp, CardBorder, RoundedCornerShape(22.dp))
                    ) {
                        CircularGauge(
                            value = 210f,
                            maxValue = 400f,
                            label = "Nitrogen (N)",
                            unit = " kg/ha",
                            statusText = "Low / Deficient",
                            gaugeColor = AlertRed
                        )
                    }

                    Card(
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(containerColor = OffWhite),
                        modifier = Modifier
                            .weight(1f)
                            .border(1.dp, CardBorder, RoundedCornerShape(22.dp))
                    ) {
                        CircularGauge(
                            value = 34f,
                            maxValue = 80f,
                            label = "Phosphorus (P)",
                            unit = " kg/ha",
                            statusText = "Medium",
                            gaugeColor = HarvestYellow
                        )
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Card(
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(containerColor = OffWhite),
                        modifier = Modifier
                            .weight(1f)
                            .border(1.dp, CardBorder, RoundedCornerShape(22.dp))
                    ) {
                        CircularGauge(
                            value = 290f,
                            maxValue = 400f,
                            label = "Potassium (K)",
                            unit = " kg/ha",
                            statusText = "High / Rich",
                            gaugeColor = LeafGreen
                        )
                    }

                    Card(
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(containerColor = OffWhite),
                        modifier = Modifier
                            .weight(1f)
                            .border(1.dp, CardBorder, RoundedCornerShape(22.dp))
                    ) {
                        CircularGauge(
                            value = 0.65f,
                            maxValue = 1.5f,
                            label = "Organic Carbon",
                            unit = "%",
                            statusText = "Medium (0.65%)",
                            gaugeColor = ForestGreen
                        )
                    }
                }
            }

            item {
                Text(
                    text = "AI Soil Amendment Prescriptions",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }

            item {
                AgriCard {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Healing, contentDescription = "Prescription", tint = ForestGreen)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Prescription #1: Nitrogen Replenishment",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = ForestGreen
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Nitrogen is currently 210 kg/ha (deficient). Top dress with 25 kg Urea mixed with 50 kg Neem Cake per acre to inhibit nitrification and slow release.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextPrimary
                    )
                }
            }

            item {
                AgriCard {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Compost, contentDescription = "Organic Carbon", tint = HarvestOrange)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Prescription #2: Boost Organic Carbon",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = HarvestOrange
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Apply 2-3 tonnes of Well-rotted Farmyard Manure (FYM) or Vermicompost before next sowing. Consider Green Manuring with Dhaincha (Sesbania).",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextPrimary
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
