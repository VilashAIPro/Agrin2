package com.example.agrinet.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agrinet.ui.components.AgriHeader
import com.example.agrinet.ui.components.StatusBadge
import com.example.ui.theme.*

data class HourlyForecast(val time: String, val temp: String, val rainProb: String, val icon: ImageVector)
data class DailyForecast(val day: String, val high: String, val low: String, val condition: String, val rainProb: String)

@Composable
fun WeatherScreen(
    location: String,
    onBack: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "SunRotate")
    val sunRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "Sun"
    )

    val hourlyList = listOf(
        HourlyForecast("Now", "31°C", "10%", Icons.Default.WbSunny),
        HourlyForecast("12 PM", "33°C", "15%", Icons.Default.WbSunny),
        HourlyForecast("2 PM", "34°C", "20%", Icons.Default.WbSunny),
        HourlyForecast("4 PM", "32°C", "35%", Icons.Default.Cloud),
        HourlyForecast("6 PM", "29°C", "50%", Icons.Default.Thunderstorm),
        HourlyForecast("8 PM", "27°C", "40%", Icons.Default.WaterDrop),
        HourlyForecast("10 PM", "25°C", "20%", Icons.Default.NightsStay)
    )

    val dailyList = listOf(
        DailyForecast("Today", "34°C", "24°C", "Scattered Clouds", "30%"),
        DailyForecast("Tomorrow", "32°C", "23°C", "Moderate Rain", "75%"),
        DailyForecast("Wednesday", "29°C", "22°C", "Heavy Showers", "85%"),
        DailyForecast("Thursday", "31°C", "23°C", "Partly Cloudy", "25%"),
        DailyForecast("Friday", "33°C", "24°C", "Sunny & Clear", "10%"),
        DailyForecast("Saturday", "35°C", "25°C", "Sunny", "10%"),
        DailyForecast("Sunday", "34°C", "25°C", "Scattered Showers", "40%")
    )

    Scaffold(
        topBar = {
            AgriHeader(
                title = "Agri Weather Forecast",
                subtitle = location,
                showBackButton = true,
                onBackClick = onBack
            )
        },
        containerColor = PureWhite,
        modifier = Modifier.testTag("screen_weather")
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
                    colors = CardDefaults.cardColors(containerColor = SkyBlueLight),
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(3.dp, RoundedCornerShape(26.dp))
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = location,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                                Text(
                                    text = "Updated 5 mins ago • OpenWeather",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = TextSecondary
                                )
                            }
                            StatusBadge(text = "AGRI SAFE", badgeColor = ForestGreen)
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.WbSunny,
                                contentDescription = "Sun",
                                tint = HarvestYellow,
                                modifier = Modifier
                                    .size(76.dp)
                                    .rotate(sunRotation)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(
                                    text = "31°C",
                                    style = MaterialTheme.typography.headlineLarge.copy(fontSize = 44.sp),
                                    fontWeight = FontWeight.ExtraBold,
                                    color = TextPrimary
                                )
                                Text(
                                    text = "Feels like 34°C • Humid",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextSecondary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                        HorizontalDivider(color = PureWhite.copy(alpha = 0.8f))
                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            WeatherStatColumn("Rain", "15%", Icons.Default.WaterDrop, SkyBlue)
                            WeatherStatColumn("Humidity", "62%", Icons.Default.Cloud, LeafGreen)
                            WeatherStatColumn("Wind", "11 km/h", Icons.Default.Air, HarvestOrange)
                            WeatherStatColumn("UV Index", "7 (High)", Icons.Default.WbSunny, HarvestYellow)
                        }
                    }
                }
            }

            item {
                Card(
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = ForestGreenLight),
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(2.dp, RoundedCornerShape(22.dp))
                ) {
                    Row(
                        modifier = Modifier.padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .clip(CircleShape)
                                .background(ForestGreen),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Safe Spray",
                                tint = PureWhite,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Safe Spraying Window: 3 PM – 6 PM",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = ForestGreenDark
                            )
                            Text(
                                text = "Wind speed is low (<12 km/h) & rain probability <20%. Ideal for foliar fertilizer application.",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextPrimary
                            )
                        }
                    }
                }
            }

            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = HarvestYellowLight),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, HarvestYellow, RoundedCornerShape(20.dp))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Alert",
                            tint = HarvestOrange,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "IMD Rain Alert for Wednesday",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = HarvestOrange
                            )
                            Text(
                                text = "Expect 45-60mm rainfall due to low pressure trough. Complete harvesting or clear drainage trenches.",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextPrimary
                            )
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Hourly Forecast",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(10.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(hourlyList) { hour ->
                        Card(
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(containerColor = OffWhite),
                            modifier = Modifier
                                .border(1.dp, CardBorder, RoundedCornerShape(18.dp))
                                .width(82.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(vertical = 14.dp, horizontal = 8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(hour.time, style = MaterialTheme.typography.labelSmall, color = TextMuted)
                                Spacer(modifier = Modifier.height(8.dp))
                                Icon(hour.icon, contentDescription = null, tint = HarvestYellow, modifier = Modifier.size(24.dp))
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(hour.temp, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextPrimary)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(hour.rainProb, style = MaterialTheme.typography.labelSmall, color = SkyBlue, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    text = "7-Day Agricultural Forecast",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }

            items(dailyList.size) { index ->
                val day = dailyList[index]
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = PureWhite),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, CardBorder, RoundedCornerShape(18.dp))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.width(100.dp)) {
                            Text(day.day, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Text(day.condition, style = MaterialTheme.typography.labelSmall, color = TextMuted)
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.WaterDrop, contentDescription = "Rain", tint = SkyBlue, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(day.rainProb, style = MaterialTheme.typography.bodyMedium, color = SkyBlue, fontWeight = FontWeight.SemiBold)
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(day.high, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(day.low, style = MaterialTheme.typography.bodyMedium, color = TextMuted)
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
fun WeatherStatColumn(
    label: String,
    value: String,
    icon: ImageVector,
    iconColor: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = label, tint = iconColor, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.height(4.dp))
        Text(value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextPrimary)
        Text(label, style = MaterialTheme.typography.labelSmall, color = TextSecondary)
    }
}
