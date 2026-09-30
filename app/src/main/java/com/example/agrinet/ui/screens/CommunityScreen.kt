package com.example.agrinet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.agrinet.ui.components.AgriHeader
import com.example.agrinet.ui.components.StatusBadge
import com.example.ui.theme.*

data class ForumPost(
    val author: String,
    val location: String,
    val crop: String,
    val content: String,
    val likes: Int,
    val replies: Int,
    val timeAgo: String,
    val aiVerified: Boolean
)

@Composable
fun CommunityScreen(
    onBack: () -> Unit,
    onAskAi: (String) -> Unit
) {
    var newPostText by remember { mutableStateOf("") }

    val posts = remember {
        mutableStateListOf(
            ForumPost(
                author = "Venkat Reddy",
                location = "Nalgonda, Telangana",
                crop = "Paddy (BPT 5204)",
                content = "My paddy seedlings are showing yellowing on leaf tips after heavy rain last Tuesday. Should I apply Zinc or wait for water to drain completely?",
                likes = 18,
                replies = 5,
                timeAgo = "2 hours ago",
                aiVerified = true
            ),
            ForumPost(
                author = "Suresh Patel",
                location = "Guntur, AP",
                crop = "Chilli (Teja)",
                content = "Using ROV-BOT for precision micro-weeding between rows today. Saved almost 3 laborers' worth of manual weeding cost in half a day!",
                likes = 42,
                replies = 12,
                timeAgo = "4 hours ago",
                aiVerified = true
            ),
            ForumPost(
                author = "Kavita Bai",
                location = "Khammam, Telangana",
                crop = "Cotton",
                content = "Is anyone noticing whitefly infestation in hybrid cotton after the humidity spike? What bio-pesticide spray has worked best for you?",
                likes = 27,
                replies = 8,
                timeAgo = "Yesterday",
                aiVerified = false
            )
        )
    }

    Scaffold(
        topBar = {
            AgriHeader(
                title = "Kisan Community",
                subtitle = "Peer Advice & AI Agronomist Answers",
                showBackButton = true,
                onBackClick = onBack
            )
        },
        containerColor = PureWhite,
        modifier = Modifier.testTag("screen_community")
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
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = ForestGreenLight),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "Ask 10,000+ Farmers & AgriNet AI",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = ForestGreenDark
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = newPostText,
                            onValueChange = { newPostText = it },
                            placeholder = { Text("Describe crop symptom, weather query, or mandi rate...") },
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = PureWhite,
                                unfocusedContainerColor = PureWhite
                            ),
                            modifier = Modifier.fillMaxWidth().testTag("input_new_post")
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(onClick = {}) {
                                    Icon(Icons.Default.PhotoCamera, contentDescription = "Attach Photo", tint = ForestGreen)
                                }
                                IconButton(onClick = {}) {
                                    Icon(Icons.Default.Mic, contentDescription = "Voice Input", tint = ForestGreen)
                                }
                            }
                            Button(
                                onClick = {
                                    if (newPostText.isNotBlank()) {
                                        posts.add(
                                            0,
                                            ForumPost(
                                                author = "Farmer Ramesh",
                                                location = "Warangal, Telangana",
                                                crop = "Chilli & Paddy",
                                                content = newPostText,
                                                likes = 0,
                                                replies = 0,
                                                timeAgo = "Just now",
                                                aiVerified = true
                                            )
                                        )
                                        onAskAi(newPostText)
                                        newPostText = ""
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = ForestGreen),
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier.testTag("btn_post_community")
                            ) {
                                Text("Post Question", color = PureWhite, style = MaterialTheme.typography.labelMedium)
                            }
                        }
                    }
                }
            }

            items(posts) { post ->
                Card(
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = PureWhite),
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
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(ForestGreenLight),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = post.author.first().toString(),
                                        fontWeight = FontWeight.Bold,
                                        color = ForestGreen
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = post.author,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary
                                    )
                                    Text(
                                        text = post.location + " • " + post.crop,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = TextMuted
                                    )
                                }
                            }
                            if (post.aiVerified) {
                                StatusBadge(text = "AI VERIFIED", badgeColor = LeafGreen)
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = post.content,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextPrimary
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = post.timeAgo,
                                style = MaterialTheme.typography.labelSmall,
                                color = TextMuted
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.ThumbUp, contentDescription = "Like", tint = ForestGreen, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(post.likes.toString(), style = MaterialTheme.typography.labelSmall, color = TextSecondary)

                                Spacer(modifier = Modifier.width(16.dp))

                                Icon(Icons.Default.Comment, contentDescription = "Reply", tint = SkyBlue, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(post.replies.toString(), style = MaterialTheme.typography.labelSmall, color = TextSecondary)
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
