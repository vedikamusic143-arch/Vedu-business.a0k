package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun BusinessPulseGaugeCard(
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
                Text(
                    text = "Business Pulse",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = VeduTextPrimary
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0x3310B981))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "Real-time Telemetry",
                        style = MaterialTheme.typography.labelSmall,
                        color = VeduEmerald,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Gauge + Metrics Split
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Circular Gauge for Overall Health (86/100)
                Box(
                    modifier = Modifier.size(110.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(100.dp)) {
                        val strokeWidth = 10.dp.toPx()
                        val diameter = size.minDimension - strokeWidth
                        val topLeft = Offset(strokeWidth / 2, strokeWidth / 2)
                        val arcSize = Size(diameter, diameter)

                        // Background track arc
                        drawArc(
                            color = Color(0xFF1E293B),
                            startAngle = 140f,
                            sweepAngle = 260f,
                            useCenter = false,
                            topLeft = topLeft,
                            size = arcSize,
                            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                        )

                        // Active Glowing Arc (86% of 260° = 223.6°)
                        drawArc(
                            brush = Brush.sweepGradient(
                                listOf(Color(0xFF00F0FF), Color(0xFF10B981), Color(0xFF3B82F6))
                            ),
                            startAngle = 140f,
                            sweepAngle = 224f,
                            useCenter = false,
                            topLeft = topLeft,
                            size = arcSize,
                            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = "86",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Black,
                                color = VeduTextPrimary
                            )
                            Text(
                                text = "/100",
                                style = MaterialTheme.typography.labelSmall,
                                color = VeduTextMuted,
                                modifier = Modifier.padding(bottom = 3.dp)
                            )
                        }
                        Text(
                            text = "Overall Health",
                            style = MaterialTheme.typography.labelSmall,
                            color = VeduTextSecondary,
                            fontSize = 9.sp
                        )
                        Text(
                            text = "↑ 12%",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = VeduEmerald,
                            fontSize = 10.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Key metrics columns
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MetricMiniItem(label = "Revenue", value = "₹ 4,82,000", delta = "↑ 18%", deltaColor = VeduEmerald)
                        MetricMiniItem(label = "Customers", value = "2,430", delta = "↑ 14%", deltaColor = VeduEmerald)
                        MetricMiniItem(label = "Retention", value = "78%", delta = "↑ 9%", deltaColor = VeduEmerald)
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(VeduBorder)
                    )

                    // Status Pills: Marketing, Operations, Cash Flow
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        StatusDotItem(label = "Marketing", status = "Good")
                        StatusDotItem(label = "Operations", status = "Stable")
                        StatusDotItem(label = "Cash Flow", status = "Healthy")
                    }
                }
            }
        }
    }
}

@Composable
private fun MetricMiniItem(
    label: String,
    value: String,
    delta: String,
    deltaColor: Color
) {
    Column {
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = VeduTextMuted, fontSize = 10.sp)
        Text(text = value, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = VeduTextPrimary)
        Text(text = delta, style = MaterialTheme.typography.labelSmall, color = deltaColor, fontWeight = FontWeight.SemiBold, fontSize = 9.sp)
    }
}

@Composable
private fun StatusDotItem(
    label: String,
    status: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(VeduEmerald)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Column {
            Text(text = label, style = MaterialTheme.typography.labelSmall, color = VeduTextMuted, fontSize = 9.sp)
            Text(text = status, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Medium, color = VeduEmerald, fontSize = 9.sp)
        }
    }
}

@Composable
fun RevenueOverviewChartCard(
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
                Text(
                    text = "Revenue Overview",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = VeduTextPrimary
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = VeduSurfaceLight
                    ) {
                        Text(
                            text = "Last 30 Days ▾",
                            style = MaterialTheme.typography.labelSmall,
                            color = VeduTextSecondary,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = "↑ 18%",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = VeduEmerald
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Wave Chart with grid lines and gradient fill
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height

                    // Grid Horizontal lines
                    val lineCount = 3
                    for (i in 0..lineCount) {
                        val y = h * (i / lineCount.toFloat())
                        drawLine(
                            color = Color(0xFF1B283E),
                            start = Offset(0f, y),
                            end = Offset(w, y),
                            strokeWidth = 1f
                        )
                    }

                    // Smooth Wave Path
                    val wavePath = Path().apply {
                        moveTo(0f, h * 0.75f)
                        cubicTo(w * 0.20f, h * 0.70f, w * 0.35f, h * 0.50f, w * 0.50f, h * 0.55f)
                        cubicTo(w * 0.65f, h * 0.60f, w * 0.80f, h * 0.25f, w, h * 0.18f)
                    }

                    // Fill under curve
                    val fillPath = Path().apply {
                        addPath(wavePath)
                        lineTo(w, h)
                        lineTo(0f, h)
                        close()
                    }

                    drawPath(
                        path = fillPath,
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0x5500F0FF), Color(0x111D4ED8), Color.Transparent),
                            startY = 0f,
                            endY = h
                        )
                    )

                    // Wave Line
                    drawPath(
                        path = wavePath,
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(0xFF38BDF8), Color(0xFF00F0FF), Color(0xFF60A5FA))
                        ),
                        style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                    )

                    // Endpoint dot
                    drawCircle(
                        color = Color(0xFF00F0FF),
                        radius = 4.dp.toPx(),
                        center = Offset(w, h * 0.18f)
                    )
                    drawCircle(
                        color = Color.White,
                        radius = 2.dp.toPx(),
                        center = Offset(w, h * 0.18f)
                    )
                }
            }

            // X-Axis Timeline
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf("1 May", "8 May", "15 May", "22 May", "30 May").forEach { label ->
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall,
                        color = VeduTextMuted,
                        fontSize = 9.sp
                    )
                }
            }
        }
    }
}
