package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ActionRiskLevel
import com.example.model.IntelligenceType
import com.example.model.TodayIntelligenceItem
import com.example.ui.theme.*

@Composable
fun TodaysIntelligenceSection(
    items: List<TodayIntelligenceItem>,
    onTriggerAction: (TodayIntelligenceItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Section Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Bolt,
                    contentDescription = null,
                    tint = VeduCyan,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "TODAY'S INTELLIGENCE",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = VeduTextPrimary,
                    letterSpacing = 1.sp
                )
            }

            Surface(
                shape = RoundedCornerShape(4.dp),
                color = VeduSurfaceLight
            ) {
                Text(
                    text = "Real-time AI Synthesis",
                    style = MaterialTheme.typography.labelSmall,
                    color = VeduCyan,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Intelligence Cards
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items.forEach { item ->
                IntelligenceCard(
                    item = item,
                    onActionClick = { onTriggerAction(item) }
                )
            }
        }
    }
}

@Composable
private fun IntelligenceCard(
    item: TodayIntelligenceItem,
    onActionClick: () -> Unit
) {
    val (typeColor, typeBg, typeIcon) = when (item.type) {
        IntelligenceType.OPPORTUNITY -> Triple(VeduCyan, Color(0x2200F0FF), Icons.Default.TrendingUp)
        IntelligenceType.RISK -> Triple(VeduAmber, Color(0x22F59E0B), Icons.Default.Warning)
        IntelligenceType.SECURITY -> Triple(VeduCrimson, Color(0x22EF4444), Icons.Default.Security)
        IntelligenceType.COMPLIANCE -> Triple(VeduPurple, Color(0x228B5CF6), Icons.Default.Gavel)
        IntelligenceType.ANOMALY -> Triple(Color(0xFFEC4899), Color(0x22EC4899), Icons.Default.CrisisAlert)
        IntelligenceType.TASK -> Triple(VeduEmerald, Color(0x2210B981), Icons.Default.CheckCircle)
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("intelligence_card_${item.id}"),
        shape = RoundedCornerShape(10.dp),
        color = VeduSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, VeduBorder)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Type badge & Confidence
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(typeBg)
                            .padding(horizontal = 6.dp, vertical = 3.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = typeIcon,
                                contentDescription = null,
                                tint = typeColor,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = item.type.label.uppercase(),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = typeColor
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = item.impactEstimate,
                        style = MaterialTheme.typography.labelSmall,
                        color = VeduTextPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // Confidence Score
                Text(
                    text = "Confidence ${item.confidenceScore}%",
                    style = MaterialTheme.typography.labelSmall,
                    color = VeduTextMuted
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Title
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = VeduTextPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Description
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodyMedium,
                color = VeduTextSecondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Why it matters & Evidence Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(6.dp))
                    .background(VeduSurfaceLight)
                    .padding(8.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row {
                        Text(
                            text = "Why It Matters: ",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = VeduCyan
                        )
                        Text(
                            text = item.whyItMatters,
                            style = MaterialTheme.typography.labelSmall,
                            color = VeduTextSecondary
                        )
                    }

                    Row {
                        Text(
                            text = "Evidence Source: ",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = VeduTextMuted
                        )
                        Text(
                            text = item.evidenceSource,
                            style = MaterialTheme.typography.labelSmall,
                            color = VeduTextMuted
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Recommended Action & Firewall classification
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Risk classification pill
                Text(
                    text = item.riskLevel.label,
                    style = MaterialTheme.typography.labelSmall,
                    color = when (item.riskLevel) {
                        ActionRiskLevel.LOW -> VeduEmerald
                        ActionRiskLevel.MEDIUM -> VeduAmber
                        ActionRiskLevel.HIGH -> VeduCrimson
                    },
                    fontWeight = FontWeight.Medium
                )

                // Action CTA
                Button(
                    onClick = onActionClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VeduSurfaceHighlight,
                        contentColor = VeduCyan
                    ),
                    shape = RoundedCornerShape(6.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                    modifier = Modifier.height(30.dp)
                ) {
                    Text(
                        text = "Take Action",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
