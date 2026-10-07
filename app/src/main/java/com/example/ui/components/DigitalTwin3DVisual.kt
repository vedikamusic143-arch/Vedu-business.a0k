package com.example.ui.components

import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun DigitalTwinCityCard(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = VeduSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, VeduBorder)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Apartment,
                        contentDescription = null,
                        tint = VeduCyan,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Digital Twin City",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = VeduTextPrimary
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0x3310B981))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "Live 3D Simulation Active",
                        style = MaterialTheme.typography.labelSmall,
                        color = VeduEmerald,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Isometric 3D City Wireframe & Nodes Canvas
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF071120))
                    .border(1.dp, Color(0xFF132F4C), RoundedCornerShape(10.dp))
            ) {
                // Background grid lines
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height

                    // Isometric Grid lines
                    for (i in 0..8) {
                        val x = w * (i / 8f)
                        drawLine(
                            color = Color(0x2200F0FF),
                            start = Offset(x, 0f),
                            end = Offset(x - w * 0.25f, h),
                            strokeWidth = 1f
                        )
                        drawLine(
                            color = Color(0x223B82F6),
                            start = Offset(0f, h * (i / 8f)),
                            end = Offset(w, h * (i / 8f) + h * 0.2f),
                            strokeWidth = 1f
                        )
                    }

                    // Futuristic Building Blocks in isometric projection
                    val b1 = Path().apply {
                        moveTo(w * 0.45f, h * 0.35f)
                        lineTo(w * 0.55f, h * 0.35f)
                        lineTo(w * 0.60f, h * 0.70f)
                        lineTo(w * 0.40f, h * 0.70f)
                        close()
                    }
                    drawPath(
                        path = b1,
                        brush = Brush.verticalGradient(
                            listOf(Color(0x8800F0FF), Color(0x220284C7))
                        )
                    )
                    drawPath(
                        path = b1,
                        color = Color(0xFF00F0FF),
                        style = Stroke(1.5f)
                    )
                }

                // Overlay Telemetry Nodes from screenshot
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        CityNodeBadge("Marketing", "+22%", VeduEmerald)
                        CityNodeBadge("Revenue", "₹12.8 Cr (↑18%)", VeduCyan)
                        CityNodeBadge("Customers", "+16%", VeduEmerald)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        CityNodeBadge("Inventory", "-8%", VeduAmber)
                        CityNodeBadge("Profit Margin", "21.4% (↑5.2%)", VeduEmerald)
                        CityNodeBadge("Operations", "+12%", VeduCyan)
                    }
                }
            }
        }
    }
}

@Composable
private fun CityNodeBadge(
    title: String,
    metric: String,
    accentColor: Color
) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = Color(0xCC0B1728),
        border = androidx.compose.foundation.BorderStroke(1.dp, accentColor.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)) {
            Text(text = title, style = MaterialTheme.typography.labelSmall, color = VeduTextMuted, fontSize = 8.sp)
            Text(text = metric, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = accentColor, fontSize = 10.sp)
        }
    }
}

@Composable
fun ScenarioPriceChangeCard(
    onViewReport: () -> Unit,
    onCreateActionPlan: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = VeduSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, VeduBorder)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Scenario: Price Change",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = VeduTextPrimary
                    )
                    Text(
                        text = "What if we reduce product price by 10%?",
                        style = MaterialTheme.typography.bodyMedium,
                        color = VeduTextSecondary
                    )
                }

                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = VeduSurfaceLight
                ) {
                    Text(
                        text = "Edit ✎",
                        style = MaterialTheme.typography.labelSmall,
                        color = VeduCyan,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Simulation Status Banner
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0x2210B981))
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = VeduEmerald,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Column {
                    Text(
                        text = "Simulation Complete",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = VeduEmerald
                    )
                    Text(
                        text = "Based on your last 6 months business data",
                        style = MaterialTheme.typography.labelSmall,
                        color = VeduTextSecondary,
                        fontSize = 9.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 3-Metric Impact Box (Revenue, Cost, Margin)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ImpactBlock(
                    title = "Estimated Revenue",
                    value = "+ ₹ 42,00,000",
                    delta = "↑ 12.5%",
                    color = VeduEmerald,
                    modifier = Modifier.weight(1f)
                )
                ImpactBlock(
                    title = "Estimated Cost",
                    value = "+ ₹ 8,50,000",
                    delta = "↑ 3.2%",
                    color = VeduAmber,
                    modifier = Modifier.weight(1f)
                )
                ImpactBlock(
                    title = "Estimated Margin",
                    value = "- 2.8%",
                    delta = "(may reduce)",
                    color = VeduCrimson,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Key Insights + Probability Range
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Key Insights
                Column(
                    modifier = Modifier.weight(1.2f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = "Key Insights", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = VeduTextPrimary)
                    InsightBullet("Potential sales increase: +18%")
                    InsightBullet("Higher demand in Tier 2 cities: +24%")
                    InsightBullet("Margin pressure: -2.8%")
                    InsightBullet("Inventory risk: Moderate")
                }

                // Probability Range gauge
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = VeduSurfaceLight,
                    modifier = Modifier.weight(0.8f)
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Confidence", style = MaterialTheme.typography.labelSmall, color = VeduTextMuted, fontSize = 9.sp)
                        Text(text = "78%", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black, color = VeduCyan)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Best Case: +18%", style = MaterialTheme.typography.labelSmall, color = VeduEmerald, fontSize = 8.sp)
                        Text(text = "Expected: +12.5%", style = MaterialTheme.typography.labelSmall, color = VeduCyan, fontSize = 8.sp)
                        Text(text = "Worst Case: +5%", style = MaterialTheme.typography.labelSmall, color = VeduAmber, fontSize = 8.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onViewReport,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, VeduBorder)
                ) {
                    Text(text = "View Full Report", style = MaterialTheme.typography.labelSmall, color = VeduTextPrimary)
                }

                Button(
                    onClick = onCreateActionPlan,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = VeduCyan, contentColor = VeduObsidian)
                ) {
                    Text(text = "Create Action Plan", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun ImpactBlock(
    title: String,
    value: String,
    delta: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = VeduSurfaceLight
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = title, style = MaterialTheme.typography.labelSmall, color = VeduTextMuted, fontSize = 8.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = value, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = color, fontSize = 10.sp)
            Text(text = delta, style = MaterialTheme.typography.labelSmall, color = VeduTextSecondary, fontSize = 8.sp)
        }
    }
}

@Composable
private fun InsightBullet(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(VeduCyan)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = text, style = MaterialTheme.typography.labelSmall, color = VeduTextSecondary, fontSize = 9.sp)
    }
}
