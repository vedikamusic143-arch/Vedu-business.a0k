package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class IntelligenceStep(
    val stepNumber: Int,
    val name: String,
    val tag: String,
    val icon: ImageVector,
    val description: String,
    val liveMetric: String,
    val status: String
)

val veduIntelligenceSteps = listOf(
    IntelligenceStep(
        stepNumber = 1,
        name = "DATA",
        tag = "Ingestion",
        icon = Icons.Default.Dns,
        description = "Continuous stream from ERP, Stripe, Shopify, CRM and encrypted Digital Vault.",
        liveMetric = "6/6 Connectors Active • 4,820 Events/hr",
        status = "Healthy"
    ),
    IntelligenceStep(
        stepNumber = 2,
        name = "UNDERSTAND",
        tag = "Cognitive Synthesis",
        icon = Icons.Default.Psychology,
        description = "Contextual semantic graph building business memory and entity relationships.",
        liveMetric = "Tenant Memory: 1,240 nodes mapped",
        status = "Synchronized"
    ),
    IntelligenceStep(
        stepNumber = 3,
        name = "PREDICT",
        tag = "Elasticity Models",
        icon = Icons.Default.TrendingUp,
        description = "Machine learning forecasts for sales trajectory, churn hazard, and demand spikes.",
        liveMetric = "Demand Forecast: +24% Festive Q4",
        status = "89% Confidence"
    ),
    IntelligenceStep(
        stepNumber = 4,
        name = "SIMULATE",
        tag = "Digital Twin Sandbox",
        icon = Icons.Default.Science,
        description = "Runs Monte Carlo sandbox simulations before any operational change touches production.",
        liveMetric = "Scenario: +8% Price → +6.4% Rev",
        status = "Sandbox Ready"
    ),
    IntelligenceStep(
        stepNumber = 5,
        name = "RECOMMEND",
        tag = "Strategic Intelligence",
        icon = Icons.Default.Lightbulb,
        description = "Identifies high-yield opportunities and flags operational anomalies.",
        liveMetric = "3 Recommended Actions Pending",
        status = "Actionable"
    ),
    IntelligenceStep(
        stepNumber = 6,
        name = "HUMAN APPROVAL",
        tag = "Trust Firewall",
        icon = Icons.Default.Security,
        description = "Zero consequential actions execute autonomously. Requires explicit human manager sign-off.",
        liveMetric = "2 Items Intercepted in Queue",
        status = "Firewall Engaged"
    ),
    IntelligenceStep(
        stepNumber = 7,
        name = "EXECUTE",
        tag = "Scoped Automation",
        icon = Icons.Default.PlayArrow,
        description = "Verified agents invoke permission-bounded APIs with immutable cryptographic signatures.",
        liveMetric = "Last Execution: 14m ago via Sales Agent",
        status = "Audited"
    ),
    IntelligenceStep(
        stepNumber = 8,
        name = "MEASURE",
        tag = "Real-time Telemetry",
        icon = Icons.Default.Speed,
        description = "Tracks actual financial and operational outcomes against simulation predictions.",
        liveMetric = "Actual Margin: +2.1% (Predicted +2.4%)",
        status = "Calibrated"
    ),
    IntelligenceStep(
        stepNumber = 9,
        name = "LEARN",
        tag = "Private Adaptation",
        icon = Icons.Default.AutoFixHigh,
        description = "Refines localized model weights with strict tenant data isolation (no cross-leakage).",
        liveMetric = "Accuracy Drift: -0.2% (Self-correcting)",
        status = "Optimal"
    ),
    IntelligenceStep(
        stepNumber = 10,
        name = "IMPROVE",
        tag = "Autonomous Refinement",
        icon = Icons.Default.Upgrade,
        description = "Continuous compounding efficiency across inventory, cash flow, and customer retention.",
        liveMetric = "Cumulative Efficiency Gain: +18.4%",
        status = "Compounding"
    )
)

@Composable
fun IntelligenceLoopVisualizer(
    onRunCycleCompleted: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedStepIndex by remember { mutableStateOf(0) }
    var isSimulatingCycle by remember { mutableStateOf(false) }
    var activeCycleStep by remember { mutableStateOf<Int?>(null) }
    val coroutineScope = rememberCoroutineScope()

    val selectedStep = veduIntelligenceSteps[selectedStepIndex]

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .testTag("intelligence_loop_card"),
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
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0x3300F0FF)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Loop,
                            contentDescription = null,
                            tint = VeduCyan,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "VEDU INTELLIGENCE LOOP",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = VeduTextPrimary
                        )
                        Text(
                            text = "10-Step Autonomous Governance & Learning Loop",
                            style = MaterialTheme.typography.labelSmall,
                            color = VeduCyan,
                            fontSize = 10.sp
                        )
                    }
                }

                Button(
                    onClick = {
                        if (!isSimulatingCycle) {
                            coroutineScope.launch {
                                isSimulatingCycle = true
                                for (i in 0 until 10) {
                                    activeCycleStep = i
                                    selectedStepIndex = i
                                    delay(450)
                                }
                                isSimulatingCycle = false
                                activeCycleStep = null
                                onRunCycleCompleted("Completed 10-step VEDU Intelligence Loop cycle across all active business nodes.")
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSimulatingCycle) VeduSurfaceHighlight else VeduCyan,
                        contentColor = if (isSimulatingCycle) VeduCyan else VeduObsidian
                    ),
                    shape = RoundedCornerShape(6.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                    modifier = Modifier.testTag("run_cycle_button")
                ) {
                    if (isSimulatingCycle) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(12.dp),
                            color = VeduCyan,
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Step ${(activeCycleStep ?: 0) + 1}/10...",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Run Cycle",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Step Navigation Horizontal Track
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                veduIntelligenceSteps.forEachIndexed { index, step ->
                    val isSelected = selectedStepIndex == index
                    val isActiveInCycle = activeCycleStep == index
                    val stepColor by animateColorAsState(
                        targetValue = when {
                            isActiveInCycle -> VeduAmber
                            isSelected -> VeduCyan
                            else -> VeduTextMuted
                        },
                        animationSpec = tween(300),
                        label = "step_color"
                    )

                    Surface(
                        onClick = { selectedStepIndex = index },
                        shape = RoundedCornerShape(8.dp),
                        color = when {
                            isActiveInCycle -> Color(0x33F59E0B)
                            isSelected -> VeduSurfaceHighlight
                            else -> VeduMidnight
                        },
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isSelected || isActiveInCycle) stepColor else VeduBorder
                        ),
                        modifier = Modifier.testTag("intel_step_$index")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(18.dp)
                                    .clip(CircleShape)
                                    .background(stepColor.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${step.stepNumber}",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = stepColor
                                )
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = step.name,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) VeduTextPrimary else VeduTextSecondary
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Active Step Detail Card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                color = VeduMidnight,
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1E293B))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = selectedStep.icon,
                                contentDescription = null,
                                tint = VeduCyan,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Step ${selectedStep.stepNumber}: ${selectedStep.name}",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = VeduTextPrimary
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0x3310B981)
                        ) {
                            Text(
                                text = selectedStep.status,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = VeduEmerald,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = selectedStep.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = VeduTextSecondary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFF0D1829))
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Analytics,
                            contentDescription = null,
                            tint = VeduCyan,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Live Telemetry: ${selectedStep.liveMetric}",
                            style = MaterialTheme.typography.labelSmall,
                            color = VeduTextPrimary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}
