package com.example.agrinet.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.agrinet.ui.components.AgriCard
import com.example.agrinet.ui.components.AgriHeader
import com.example.agrinet.ui.components.StatusBadge
import com.example.agrinet.ui.viewmodel.DiseaseScanResult
import com.example.ui.theme.*

@Composable
fun DiseaseDetectionScreen(
    isScanning: Boolean,
    scanResult: DiseaseScanResult?,
    onScanRequested: (String) -> Unit,
    onBack: () -> Unit
) {
    var selectedCropSample by remember { mutableStateOf("Tomato") }
    val cropSamples = listOf("Tomato", "Rice", "Cotton", "Healthy")

    val infiniteTransition = rememberInfiniteTransition(label = "LaserScan")
    val laserProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Laser"
    )

    Scaffold(
        topBar = {
            AgriHeader(
                title = "AI Disease Scanner",
                subtitle = "Optical Diagnosis • Real-time Remedy",
                showBackButton = true,
                onBackClick = onBack
            )
        },
        containerColor = PureWhite,
        modifier = Modifier.testTag("screen_disease_detection")
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
                    text = "Scan Crop Leaf",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "Point camera at leaf lesion or select a test sample to diagnose.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(OffWhite)
                        .border(2.dp, if (isScanning) LeafGreen else CardBorder, RoundedCornerShape(24.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(90.dp)
                                .clip(CircleShape)
                                .background(ForestGreenLight),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = "Camera",
                                tint = ForestGreen,
                                modifier = Modifier.size(46.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = if (isScanning) "AI Deep Learning Analysis..." else "Target: " + selectedCropSample + " Leaf",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (isScanning) ForestGreen else TextPrimary
                        )
                        Text(
                            text = if (isScanning) "Extracting spectral color features & lesion contours" else "Ready to capture high-res frame",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextMuted
                        )
                    }

                    if (isScanning) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val yPos = size.height * laserProgress
                            drawLine(
                                brush = Brush.linearGradient(
                                    colors = listOf(Color.Transparent, LeafGreen, ForestGreen, Color.Transparent)
                                ),
                                start = Offset(0f, yPos),
                                end = Offset(size.width, yPos),
                                strokeWidth = 6.dp.toPx()
                            )
                        }
                    }
                }
            }

            item {
                Column {
                    Text(
                        text = "Choose Sample Leaf or Upload:",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        cropSamples.forEach { sample ->
                            val isSelected = selectedCropSample == sample
                            FilterChip(
                                selected = isSelected,
                                onClick = {
                                    selectedCropSample = sample
                                    onScanRequested(sample)
                                },
                                label = { Text(sample) },
                                leadingIcon = {
                                    if (isSelected) {
                                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                                    }
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = ForestGreen,
                                    selectedLabelColor = PureWhite
                                ),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            item {
                Button(
                    onClick = { onScanRequested(selectedCropSample) },
                    enabled = !isScanning,
                    colors = ButtonDefaults.buttonColors(containerColor = ForestGreen),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("btn_capture_scan")
                ) {
                    Icon(Icons.Default.CenterFocusStrong, contentDescription = "Scan", tint = PureWhite)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isScanning) "Scanning..." else "Scan Leaf Now",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = PureWhite
                    )
                }
            }

            scanResult?.let { res ->
                item {
                    Text(
                        text = "Diagnosis & Treatment Plan",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }

                item {
                    Card(
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (res.severity == "High") AlertRedLight else ForestGreenLight
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(2.dp, RoundedCornerShape(24.dp))
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = res.crop + " Diagnostic",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = if (res.severity == "High") AlertRed else ForestGreenDark
                                    )
                                    Text(
                                        text = res.diseaseName,
                                        style = MaterialTheme.typography.headlineSmall,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = TextPrimary
                                    )
                                }

                                StatusBadge(
                                    text = res.confidence.toString() + "% Confident",
                                    badgeColor = if (res.severity == "High") AlertRed else ForestGreen
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "Severity Level:",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                                StatusBadge(
                                    text = res.severity.uppercase(),
                                    badgeColor = when (res.severity) {
                                        "High" -> AlertRed
                                        "Medium" -> HarvestOrange
                                        else -> LeafGreen
                                    }
                                )
                            }
                        }
                    }
                }

                item {
                    AgriCard {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Eco, contentDescription = "Organic", tint = ForestGreen)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Organic & Biological Remedy",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = ForestGreen
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = res.organicTreatment,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextPrimary
                        )
                    }
                }

                item {
                    AgriCard {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Science, contentDescription = "Chemical", tint = HarvestOrange)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Chemical Spray Solution (ICAR Recommended)",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = HarvestOrange
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = res.chemicalTreatment,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextPrimary
                        )
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
                                Icon(Icons.Default.ContactPhone, contentDescription = "Center", tint = SkyBlue)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Nearby Krishi Vigyan Kendra (KVK)",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "KVK Warangal • Agricultural Officer Dr. S. Rao • Kisan Toll Free: 1800-180-1551",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondary
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Button(
                                onClick = {},
                                colors = ButtonDefaults.buttonColors(containerColor = SkyBlue),
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier.height(40.dp)
                            ) {
                                Icon(Icons.Default.Phone, contentDescription = "Call", tint = PureWhite, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Call KVK Helpline", color = PureWhite, style = MaterialTheme.typography.labelMedium)
                            }
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
