package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BusinessPulse
import com.example.ui.theme.*

@Composable
fun BusinessPulseCards(
    businessName: String,
    pulse: BusinessPulse,
    onAskVeduClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Welcome banner matching screenshot: "Good Morning, Aarav Sharma - Here's what's happening with your business today."
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Good Morning, $businessName",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = VeduTextPrimary
                )
                Text(
                    text = "Here's what's happening with your business today.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = VeduTextSecondary
                )
            }

            // User profile avatar chip
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(VeduCyanGlow)
                    .border(1.dp, VeduCyan, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = businessName.take(2).uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = VeduCyan
                )
            }
        }

        // Central ASK VEDU Hero Bar with mic and prompt suggestion chips
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("ask_vedu_central_button"),
            shape = RoundedCornerShape(12.dp),
            color = VeduSurface,
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1E3A8A))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(VeduSurfaceLight)
                        .clickable { onAskVeduClick() }
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = VeduCyan,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Ask VEDU anything...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = VeduTextMuted
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = "Voice Input",
                        tint = VeduCyan,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Suggestion chips matching screenshot:
                // "Show me today's insights", "Analyze my sales", "What are the opportunities?"
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    SuggestionPill("Show today's insights", onAskVeduClick)
                    SuggestionPill("Analyze my sales", onAskVeduClick)
                    SuggestionPill("Opportunities", onAskVeduClick)
                }
            }
        }

        // Business Pulse Overall Health Gauge & Real-time Metrics Card
        BusinessPulseGaugeCard()

        // 30-Day Revenue Overview Wave Chart Card
        RevenueOverviewChartCard()
    }
}

@Composable
private fun SuggestionPill(
    text: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        color = VeduSurfaceHighlight,
        border = androidx.compose.foundation.BorderStroke(1.dp, VeduBorderSubtle)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = VeduTextCyan,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            fontSize = 9.sp
        )
    }
}
