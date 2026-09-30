package com.example.agrinet.ui.screens

import androidx.compose.animation.core.*
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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.agrinet.ui.components.AgriCard
import com.example.agrinet.ui.components.AgriHeader
import com.example.ui.theme.*

@Composable
fun RovBotScreen(
    battery: Int,
    solarPower: Int,
    status: String,
    progress: Float,
    weedsCount: Int,
    onStartMission: (String) -> Unit,
    onPauseMission: () -> Unit,
    onReturnHome: () -> Unit,
    onBack: () -> Unit
) {
    var selectedMissionType by remember { mutableStateOf("Precision Weeding") }
    val missionTypes = listOf("Precision Weeding", "Soil Sampling", "Night Patrol", "Targeted Spray")

    val infiniteTransition = rememberInfiniteTransition(label = "RadarSweep")
    val sweepAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "Sweep"
    )

    Scaffold(
        topBar = {
            AgriHeader(
                title = "ROV-BOT Control Hub",
                subtitle = "Autonomous Field Rover #01",
                showBackButton = true,
                onBackClick = onBack
            )
        },
        containerColor = PureWhite,
        modifier = Modifier.testTag("screen_rov_bot")
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
                    colors = CardDefaults.cardColors(containerColor = OffWhite),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .shadow(4.dp, RoundedCornerShape(26.dp))
                        .border(1.dp, CardBorder, RoundedCornerShape(26.dp))
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val cx = size.width / 2
                            val cy = size.height / 2

                            drawCircle(color = LeafGreenLight.copy(alpha = 0.4f), radius = 100.dp.toPx(), center = Offset(cx, cy), style = Stroke(1.dp.toPx()))
                            drawCircle(color = LeafGreenLight.copy(alpha = 0.3f), radius = 65.dp.toPx(), center = Offset(cx, cy), style = Stroke(1.dp.toPx()))

                            drawArc(
                                color = ForestGreen.copy(alpha = 0.25f),
                                startAngle = sweepAngle,
                                sweepAngle = 45f,
                                useCenter = true,
                                topLeft = Offset(cx - 100.dp.toPx(), cy - 100.dp.toPx()),
                                size = Size(200.dp.toPx(), 200.dp.toPx())
                            )

                            drawRoundRect(
                                color = Color(0xFF2E7D32),
                                topLeft = Offset(cx - 36.dp.toPx(), cy - 44.dp.toPx()),
                                size = Size(72.dp.toPx(), 88.dp.toPx()),
                                cornerRadius = CornerRadius(16.dp.toPx(), 16.dp.toPx())
                            )

                            drawRoundRect(
                                color = Color(0xFF1565C0),
                                topLeft = Offset(cx - 28.dp.toPx(), cy - 28.dp.toPx()),
                                size = Size(56.dp.toPx(), 56.dp.toPx()),
                                cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
                            )

                            val wheelW = 12.dp.toPx()
                            val wheelH = 34.dp.toPx()
                            drawRoundRect(Color.DarkGray, Offset(cx - 48.dp.toPx(), cy - 40.dp.toPx()), Size(wheelW, wheelH), CornerRadius(4f, 4f))
                            drawRoundRect(Color.DarkGray, Offset(cx + 36.dp.toPx(), cy - 40.dp.toPx()), Size(wheelW, wheelH), CornerRadius(4f, 4f))
                            drawRoundRect(Color.DarkGray, Offset(cx - 48.dp.toPx(), cy + 6.dp.toPx()), Size(wheelW, wheelH), CornerRadius(4f, 4f))
                            drawRoundRect(Color.DarkGray, Offset(cx + 36.dp.toPx(), cy + 6.dp.toPx()), Size(wheelW, wheelH), CornerRadius(4f, 4f))

                            drawCircle(Color(0xFFF9A825), radius = 6.dp.toPx(), center = Offset(cx, cy - 40.dp.toPx()))
                        }

                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = PureWhite.copy(alpha = 0.9f),
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(if (status == "MISSION_RUNNING") LeafGreen else HarvestYellow)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = status,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
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
                    TelemetryCard("Battery", battery.toString() + "%", Icons.Default.BatteryChargingFull, LeafGreen, Modifier.weight(1f))
                    TelemetryCard("Solar Active", solarPower.toString() + "W", Icons.Default.SolarPower, HarvestOrange, Modifier.weight(1f))
                    TelemetryCard("GPS RTK", "±2 cm", Icons.Default.GpsFixed, SkyBlue, Modifier.weight(1f))
                    TelemetryCard("LiDAR 360", "Online", Icons.Default.Sensors, ForestGreen, Modifier.weight(1f))
                }
            }

            item {
                Text(
                    text = "Select Autonomous Mission",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    missionTypes.take(2).forEach { m ->
                        val isSelected = selectedMissionType == m
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedMissionType = m },
                            label = { Text(m) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = ForestGreen,
                                selectedLabelColor = PureWhite
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    missionTypes.drop(2).forEach { m ->
                        val isSelected = selectedMissionType == m
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedMissionType = m },
                            label = { Text(m) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = ForestGreen,
                                selectedLabelColor = PureWhite
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            item {
                AgriCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedMissionType,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = progress.toInt().toString() + "% Complete",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = ForestGreen
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { (progress / 100f).coerceIn(0f, 1f) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = ForestGreen,
                        trackColor = ForestGreenLight
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Weeds Eliminated: " + weedsCount + " targets",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium,
                            color = TextSecondary
                        )
                        Text(
                            text = "Coverage: 1.8 / 4.5 Acres",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextMuted
                        )
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = { onStartMission(selectedMissionType) },
                        colors = ButtonDefaults.buttonColors(containerColor = ForestGreen),
                        shape = RoundedCornerShape(18.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .testTag("btn_rov_start")
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = "Start", tint = PureWhite)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Start Mission", fontWeight = FontWeight.Bold, color = PureWhite)
                    }

                    OutlinedButton(
                        onClick = onPauseMission,
                        shape = RoundedCornerShape(18.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .testTag("btn_rov_pause")
                    ) {
                        Icon(Icons.Default.Pause, contentDescription = "Pause", tint = ForestGreen)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Pause", fontWeight = FontWeight.Bold, color = ForestGreen)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedButton(
                    onClick = onReturnHome,
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("btn_rov_return_home")
                ) {
                    Icon(Icons.Default.Home, contentDescription = "Home", tint = HarvestOrange)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Return ROV-BOT to Dock / Base", fontWeight = FontWeight.Bold, color = HarvestOrange)
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun TelemetryCard(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = OffWhite),
        modifier = modifier.border(1.dp, CardBorder, RoundedCornerShape(18.dp))
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = label, tint = iconColor, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = TextPrimary)
            Text(label, style = MaterialTheme.typography.labelSmall, color = TextMuted)
        }
    }
}
