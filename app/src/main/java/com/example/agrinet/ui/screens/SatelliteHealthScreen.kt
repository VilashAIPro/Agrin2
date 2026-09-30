package com.example.agrinet.ui.screens

import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.agrinet.ui.components.AgriCard
import com.example.agrinet.ui.components.AgriHeader
import com.example.agrinet.ui.components.StatusBadge
import com.example.ui.theme.*

@Composable
fun SatelliteHealthScreen(
    onBack: () -> Unit
) {
    var timelineWeek by remember { mutableFloatStateOf(4f) }

    val weeksLabel = when (timelineWeek.toInt()) {
        1 -> "Week 1 (Sowing Phase)"
        2 -> "Week 2 (Early Vegetative)"
        3 -> "Week 3 (Tillering)"
        else -> "Week 4 (Current • Peak Canopy)"
    }

    Scaffold(
        topBar = {
            AgriHeader(
                title = "Satellite NDVI Health",
                subtitle = "Sentinel-2 & Landsat-9 Multi-spectral Radar",
                showBackButton = true,
                onBackClick = onBack
            )
        },
        containerColor = PureWhite,
        modifier = Modifier.testTag("screen_satellite_health")
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Plot Boundary NDVI Heatmap",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = "Resolution: 10m/pixel • Sentinel Hub API",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )
                    }
                    StatusBadge(text = "LIVE SATELLITE", badgeColor = SkyBlue)
                }
            }

            item {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = OffWhite),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                        .shadow(4.dp, RoundedCornerShape(24.dp))
                        .border(1.dp, CardBorder, RoundedCornerShape(24.dp))
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val w = size.width
                            val h = size.height

                            for (x in 0..10) {
                                drawLine(
                                    color = Color.LightGray.copy(alpha = 0.3f),
                                    start = Offset(x * (w / 10), 0f),
                                    end = Offset(x * (w / 10), h),
                                    strokeWidth = 1f
                                )
                            }
                            for (y in 0..10) {
                                drawLine(
                                    color = Color.LightGray.copy(alpha = 0.3f),
                                    start = Offset(0f, y * (h / 10)),
                                    end = Offset(w, y * (h / 10)),
                                    strokeWidth = 1f
                                )
                            }

                            val cellW = w * 0.18f
                            val cellH = h * 0.16f
                            val startX = w * 0.15f
                            val startY = h * 0.18f

                            drawRoundRect(
                                color = Color(0xFF2E7D32).copy(alpha = 0.85f),
                                topLeft = Offset(startX, startY),
                                size = Size(cellW * 2, cellH * 2),
                                cornerRadius = CornerRadius(12f, 12f)
                            )
                            drawRoundRect(
                                color = Color(0xFF43A047).copy(alpha = 0.85f),
                                topLeft = Offset(startX + cellW * 2 + 8, startY),
                                size = Size(cellW * 1.5f, cellH * 2),
                                cornerRadius = CornerRadius(12f, 12f)
                            )

                            drawRoundRect(
                                color = Color(0xFFF9A825).copy(alpha = 0.85f),
                                topLeft = Offset(startX, startY + cellH * 2 + 8),
                                size = Size(cellW * 1.8f, cellH * 1.4f),
                                cornerRadius = CornerRadius(12f, 12f)
                            )

                            drawRoundRect(
                                color = Color(0xFFD32F2F).copy(alpha = 0.85f),
                                topLeft = Offset(startX + cellW * 2 + 8, startY + cellH * 2 + 8),
                                size = Size(cellW * 1.5f, cellH * 1.4f),
                                cornerRadius = CornerRadius(12f, 12f)
                            )

                            val fieldPath = Path().apply {
                                moveTo(startX - 12, startY - 12)
                                lineTo(startX + cellW * 3.6f + 12, startY - 12)
                                lineTo(startX + cellW * 3.6f + 12, startY + cellH * 3.5f + 12)
                                lineTo(startX - 12, startY + cellH * 3.5f + 12)
                                close()
                            }
                            drawPath(
                                path = fieldPath,
                                color = Color(0xFF1B5E20),
                                style = Stroke(width = 4.dp.toPx())
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = PureWhite.copy(alpha = 0.9f),
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(12.dp)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text("Zoom: 18x", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                                Text("Cloud Cover: 2%", style = MaterialTheme.typography.labelSmall, color = TextMuted)
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = PureWhite.copy(alpha = 0.9f),
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.PinDrop, contentDescription = null, tint = ForestGreen, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("17.9689° N, 79.5941° E", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
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
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Timeline Progression",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = weeksLabel,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = ForestGreen
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Slider(
                            value = timelineWeek,
                            onValueChange = { timelineWeek = it },
                            valueRange = 1f..4f,
                            steps = 2,
                            colors = SliderDefaults.colors(
                                thumbColor = ForestGreen,
                                activeTrackColor = ForestGreen
                            )
                        )
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    LegendItem("Healthy (NDVI > 0.7)", Color(0xFF2E7D32), "72% area")
                    LegendItem("Moderate (0.4 - 0.7)", Color(0xFFF9A825), "18% area")
                    LegendItem("Stress (< 0.4)", Color(0xFFD32F2F), "10% area")
                }
            }

            item {
                AgriCard {
                    Text(
                        text = "Field Satellite Telemetry",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Total Area", style = MaterialTheme.typography.labelSmall, color = TextMuted)
                            Text("4.5 Acres", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextPrimary)
                        }
                        Column {
                            Text("Mean NDVI", style = MaterialTheme.typography.labelSmall, color = TextMuted)
                            Text("0.78", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = ForestGreen)
                        }
                        Column {
                            Text("Water Stress", style = MaterialTheme.typography.labelSmall, color = TextMuted)
                            Text("Low (Plot East)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = HarvestOrange)
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
fun LegendItem(
    title: String,
    color: Color,
    percentage: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(14.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Column {
            Text(title, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = TextPrimary)
            Text(percentage, style = MaterialTheme.typography.labelSmall, color = TextMuted)
        }
    }
}
