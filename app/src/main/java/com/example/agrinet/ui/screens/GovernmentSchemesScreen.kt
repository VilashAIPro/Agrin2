package com.example.agrinet.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.agrinet.ui.components.AgriHeader
import com.example.agrinet.ui.components.StatusBadge
import com.example.ui.theme.*

data class SchemeItem(
    val title: String,
    val ministry: String,
    val benefit: String,
    val eligibility: String,
    val category: String,
    val officialPortal: String
)

@Composable
fun GovernmentSchemesScreen(
    onBack: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }

    val schemes = listOf(
        SchemeItem(
            title = "PM-KISAN Samman Nidhi",
            ministry = "Ministry of Agriculture & Farmers Welfare",
            benefit = "₹6,000 / year direct benefit transfer in 3 equal instalments of ₹2,000.",
            eligibility = "All small and marginal landholding farmer families with cultivable land.",
            category = "Financial Aid",
            officialPortal = "pmkisan.gov.in"
        ),
        SchemeItem(
            title = "SMAM (Agricultural Mechanization Subsidy)",
            ministry = "Mechanization & Technology Division",
            benefit = "40% to 50% subsidy on precision agricultural machinery, ROV rovers, and spray drones.",
            eligibility = "Individual farmers, FPOs, Women self-help groups, SC/ST farmers.",
            category = "Robotics & Tech",
            officialPortal = "agrimachinery.nic.in"
        ),
        SchemeItem(
            title = "Pradhan Mantri Fasal Bima Yojana (PMFBY)",
            ministry = "Department of Agriculture and Farmers Welfare",
            benefit = "Comprehensive crop insurance against non-preventable natural risks at 1.5% - 2% premium.",
            eligibility = "All farmers growing notified crops in notified areas including sharecroppers.",
            category = "Insurance",
            officialPortal = "pmfby.gov.in"
        ),
        SchemeItem(
            title = "Soil Health Card Scheme",
            ministry = "National Mission on Sustainable Agriculture",
            benefit = "Free soil testing card issued every 2 years with crop-wise nutrient recommendations.",
            eligibility = "All agricultural landholders in rural India.",
            category = "Soil Health",
            officialPortal = "soilhealth.dac.gov.in"
        ),
        SchemeItem(
            title = "PM Krishi Sinchayee Yojana (Micro-Irrigation)",
            ministry = "Ministry of Jal Shakti & Agriculture",
            benefit = "Up to 55% - 70% subsidy on Drip and Sprinkler irrigation equipment under 'Per Drop More Crop'.",
            eligibility = "Farmers with assured water source and cultivable land.",
            category = "Irrigation",
            officialPortal = "pmksy.gov.in"
        )
    )

    val filteredSchemes = schemes.filter {
        (selectedCategory == "All" || it.category == selectedCategory) &&
        (it.title.contains(searchQuery, ignoreCase = true) || it.benefit.contains(searchQuery, ignoreCase = true))
    }

    Scaffold(
        topBar = {
            AgriHeader(
                title = "Government Schemes",
                subtitle = "Subsidies, PM-KISAN, Tech & Insurance",
                showBackButton = true,
                onBackClick = onBack
            )
        },
        containerColor = PureWhite,
        modifier = Modifier.testTag("screen_government_schemes")
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
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search schemes (e.g. Subsidy, Drone, PM-KISAN)") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = ForestGreen) },
                    shape = RoundedCornerShape(18.dp),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("input_scheme_search")
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("All", "Financial Aid", "Robotics & Tech", "Insurance", "Irrigation").forEach { cat ->
                        val isSelected = selectedCategory == cat
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedCategory = cat },
                            label = { Text(cat, style = MaterialTheme.typography.labelSmall) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = ForestGreen,
                                selectedLabelColor = PureWhite
                            )
                        )
                    }
                }
            }

            items(filteredSchemes) { scheme ->
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
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = scheme.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                                Text(
                                    text = scheme.ministry,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = TextMuted
                                )
                            }
                            StatusBadge(text = scheme.category, badgeColor = ForestGreen)
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Benefit: " + scheme.benefit,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = ForestGreenDark
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Eligibility: " + scheme.eligibility,
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Portal: " + scheme.officialPortal,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = SkyBlue
                            )
                            Button(
                                onClick = {},
                                colors = ButtonDefaults.buttonColors(containerColor = ForestGreen),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.height(36.dp)
                            ) {
                                Text("Apply Online", style = MaterialTheme.typography.labelSmall, color = PureWhite)
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
